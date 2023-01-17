package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableSet;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.CharLiteralStyle;
import org.apache.calcite.config.Lex;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.ddl.SqlColumnDeclaration;
import org.apache.calcite.sql.dialect.CalciteSqlDialect;
import org.apache.calcite.sql.dialect.PrestoSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.ddl.SqlDdlParserImpl;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liuzhifei
 * @date 2023/1/11 4:10 下午
 */
public class SQLUtils {

    public static LinkedHashMap<String,Class<?>> getFieldsByJSONObject(String json){
        LinkedHashMap<String,Class<?>> fields = new LinkedHashMap<>();
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

    public static ScannableTable getTableByCreateSql(String sql) throws SqlParseException {
        SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL).withParserFactory(SqlDdlParserImpl.FACTORY);
        SqlParser sqlParser = SqlParser.create(sql, config);

        SqlNode sqlNode = sqlParser.parseStmt();
        SqlKind kind = sqlNode.getKind();
        switch (kind){
            case CREATE_TABLE:
                handleCreate(sqlNode);
                break;
            default:
                break;
        }
        return null;
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
                "  `name` varchar,\n" +
                "  `list` `ARRAY<ROW<course STRING,score INT>>`\n" +
                ")");
    }
}
