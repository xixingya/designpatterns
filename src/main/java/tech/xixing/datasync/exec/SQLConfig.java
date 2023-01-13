package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import org.apache.calcite.sql.parser.SqlParseException;
import tech.xixing.datasync.adapter.JsonSchema;
import tech.xixing.datasync.udf.AviatorUdf;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @author liuzhifei
 * @date 2023/1/11 10:29 上午
 */
@Data
public class SQLConfig {
    private String sql = "";

    private JsonSchema jsonSchema;

    private Properties properties;

    private SchemaPlus rootSchema;

    private PreparedStatement statement;

    private Connection connection;

    private LinkedHashMap<String,Class<?>> fields;


    public SQLConfig(String sql, String table) throws SQLException, SqlParseException {
        this(sql,table,null);
    }

    public SQLConfig(String sql, String table, LinkedHashMap<String,Class<?>> fields) throws SQLException, SqlParseException {
        // 把mysql语法的sql转成calcite的语法
        this.sql = SQLUtils.changeSQL2StandardCalciteSQL(sql);
        this.fields = fields;
        Properties properties = new Properties();
        // 需要添加这个去除大小写，要不然自定义的udf会被转成大写从而报没有这个函数的错误
        properties.setProperty("caseSensitive", "false");
        connection = DriverManager.getConnection("jdbc:calcite:", properties);
        CalciteConnection optiqConnection = connection.unwrap(CalciteConnection.class);
        rootSchema = optiqConnection.getRootSchema();
        jsonSchema = new JsonSchema(table, "{\n" +
                "  \"uid\": \"223621008271061580\",\n" +
                "  \"expireTime\": \"1673366399000\",\n" +
                "  \"createTime\": \"1673280060428\",\n" +
                "  \"state\": \"1\"\n" +
                "}",fields);
        rootSchema.add("kafka", jsonSchema);
        rootSchema.add("aviator_func", ScalarFunctionImpl.create(AviatorUdf.class,"execute"));
        statement = connection.prepareStatement(this.sql);
    }

    public void setData(String jsonArray){
        jsonSchema.setTarget(jsonArray);
    }

    public void prepared() throws SQLException {
        statement = connection.prepareStatement(sql);
    }

    public void execute(String jsonArray) throws SQLException {
        long l = System.currentTimeMillis();
        jsonSchema.setTarget(jsonArray);
        ResultSet resultSet = statement.executeQuery();
        System.out.println("use time = "+(System.currentTimeMillis()-l));
        while (resultSet.next()) {
            JSONObject jo = new JSONObject();
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                jo.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
            }
            System.out.println(jo.toJSONString());
        }
    }
}
