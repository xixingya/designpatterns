package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableSet;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.CharLiteralStyle;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.CalciteSqlDialect;
import org.apache.calcite.sql.dialect.PrestoSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import java.util.LinkedHashMap;
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
        //通过sqlNode把mysql的语法，改成Calcite的语法
        return sqlNode.toSqlString(CalciteSqlDialect.DEFAULT).getSql();
    }
}
