package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSON;
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

    public static String changeSQL2StandardCalciteSQL(String sql) throws SqlParseException {
//        SqlParser.Config config = SqlParser.config().
//                withQuoting(Quoting.BACK_TICK)
//                .withQuotedCasing(Casing.UNCHANGED)
//                .withUnquotedCasing(Casing.UNCHANGED);
        // 使用mysql语法去解析sql
        SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL);

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
        getTableByCreateSql("CREATE TABLE ods_kafka_student_scores (\n" +
                "  `name` ROW<course STRING,score INT>,\n" +
                "  `list` ARRAY<ROW<course STRING,score INT>>\n" +
                ")");
    }
}
