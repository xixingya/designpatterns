package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableSet;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.CharLiteralStyle;
import org.apache.calcite.config.Lex;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.ddl.SqlColumnDeclaration;
import org.apache.calcite.sql.dialect.CalciteSqlDialect;
import org.apache.calcite.sql.dialect.PrestoSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.ddl.SqlDdlParserImpl;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import tech.xixing.datasync.adapter.JsonSchema;
import tech.xixing.datasync.adapter.JsonTable;
import tech.xixing.sql.parser.ddl.SqlCreateTable;
import tech.xixing.sql.parser.ddl.SqlTableColumn;
import tech.xixing.sql.parser.extend.CreateSqlParserImpl;
import tech.xixing.sql.parser.type.SqlTypeNameSpec2Type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liuzhifei
 * @date 2023/1/11 4:10 下午
 */
public class SQLUtils {

    public static LinkedHashMap<String,Object> getFieldsByJSONObject(String json){
        LinkedHashMap<String,Object> fields = new LinkedHashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(json);
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            fields.put(key,value.getClass());
        }
        return fields;
    }

    public static String transformSqlByJsonObj(String json,String tableName){
        JSONObject jsonObject = JSONObject.parseObject(json);
        StringBuilder sb = new StringBuilder();
        sb.append("create table ").append(tableName).append("(").append("\n");
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            Class<?> aClass = value.getClass();
            String type = aClass.getSimpleName();

            if(JSONArray.class.isAssignableFrom(aClass)){
                type="ARRAY<STRING>";
            }
            if(JSONObject.class.isAssignableFrom(aClass)){
                type="MAP<STRING,STRING>";
            }
            sb.append(key).append(" ").append(type).append(",").append("\n");
        }
        String substring = sb.substring(0, sb.length()-2);
        return substring+"\n)";
    }

    public static String changeSQL2StandardCalciteSQL(String sql) throws SqlParseException {
        SqlParser.Config config = SqlParser.config().
                withQuoting(Quoting.BACK_TICK)
                .withQuotedCasing(Casing.UNCHANGED)
                .withUnquotedCasing(Casing.UNCHANGED);
        // 使用mysql语法去解析sql
        // SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL);

        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseQuery();
        SqlKind kind = sqlNode.getKind();

        //通过sqlNode把mysql的语法，改成Calcite的语法
        return sqlNode.toSqlString(CalciteSqlDialect.DEFAULT).getSql();
    }

    public static LinkedHashMap<String,Object> getTableByCreateSql(String sql) throws SqlParseException {
        SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL).withParserFactory(CreateSqlParserImpl.FACTORY);
        SqlParser sqlParser = SqlParser.create(sql, config);

        SqlNode sqlNode = sqlParser.parseStmt();
        if(!(sqlNode instanceof SqlCreateTable)){
            return null;
        }
        SqlCreateTable sqlCreateTable = (SqlCreateTable)sqlNode;

        SqlNodeList columnList = sqlCreateTable.getColumnList();

        LinkedHashMap<String,Object> fields = new LinkedHashMap<>();
        for (SqlNode node : columnList) {
            SqlTableColumn sqlTableColumn = (SqlTableColumn)node;
            String name = sqlTableColumn.getName().toString();
            SqlTypeNameSpec typeNameSpec = sqlTableColumn.getType().getTypeNameSpec();
            fields.put(name,typeNameSpec);
        }
        return fields;
    }

    private static void handleCreate(SqlNode sqlNode){
        SqlCreate sqlCreate = (SqlCreate) sqlNode;
        List<SqlNode> operandList = sqlCreate.getOperandList();
        for (SqlNode node : operandList) {
            SqlKind kind = node.getKind();
            if(kind.equals(SqlKind.IDENTIFIER)){
                SqlIdentifier sqlIdentifier = (SqlIdentifier) node;
                System.out.println(sqlIdentifier.toString());
            }
            if(kind.equals(SqlKind.OTHER)){
                SqlNodeList sqlNodeList = (SqlNodeList)node;
                for (SqlNode temp : sqlNodeList) {
                    SqlColumnDeclaration sqlColumnDeclaration = (SqlColumnDeclaration) temp;
                    System.out.println(sqlColumnDeclaration.dataType.getTypeName().toString());
                }
            }
            // System.out.println(node.toSqlString(CalciteSqlDialect.DEFAULT).getSql());
        }
    }

    public static void main(String[] args) throws SqlParseException {
//        getTableByCreateSql("CREATE TABLE ods_kafka_student_scores (\n" +
//                "  `name` ROW<course STRING,score INT>,\n" +
//                "  `list` ARRAY<ROW<course STRING,score INT>>\n" +
//                ")");
        String table1 = transformSqlByJsonObj("{\n" +
                "    \"roomTag\": \"传奇王者\",\n" +
                "    \"ext\": {\n" +
                "      \"status\": 1\n" +
                "    },\n" +
                "    \"list\": [{\"name\": \"qaq\",\"desc\": \"hello\"},{\"name\": \"wang\",\"desc\": \"world\"},{\"desc\": \"good\",\"name\": \"li\"}],\n" +
                "    \"catTeamDesc\": \"不限/娱乐局/不限/五排\",\n" +
                "    \"teamChatId\": \"0\",\n" +
                "    \"roomId\": \"0167944df1424738a34051940268d17c\",\n" +
                "    \"uid\": \"223\",\n" +
                "    \"roomStatus\": \"1\",\n" +
                "    \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "    \"catName\": \"王者荣耀\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672742306444\",\n" +
                "    \"roomTitle\": \"巅峰2500 技术 98胜 车队\",\n" +
                "    \"sourceFrom\": \"0\"\n" +
                "  }", "table1");

        LinkedHashMap<String, Object> fields = getTableByCreateSql(table1);


        System.out.println(table1);

    }
}
