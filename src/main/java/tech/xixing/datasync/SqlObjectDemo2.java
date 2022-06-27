package tech.xixing.datasync;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import com.alibaba.fastjson.JSONObject;
import tech.xixing.datasync.adapter.JsonSchema;

/**
 * @author liuzhifei
 * @date 2022/6/24 4:15 下午
 */
public class SqlObjectDemo2 {

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();
        run();
        long duration = System.currentTimeMillis() - begin;
        System.out.println("total:" + duration);
    }

    public static void run() throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:calcite:");
        CalciteConnection optiqConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = optiqConnection.getRootSchema();

        String json = "[{\"CUST_ID\":{\"a\":1},\"PROD_ID\":23.56,\"USER_ID\":300,\"USER_NAME\":\"user1\"},"
                + "{\"USER_ID\":310,\"CUST_ID\":{\"a\":2},\"PROD_ID\":210.45,\"USER_NAME\":\"user2\"},"
                + "{\"USER_ID\":320,\"CUST_ID\":{\"a\":3},\"PROD_ID\":210.46,\"USER_NAME\":\"user3\"},"
                + "{\"USER_ID\":330,\"CUST_ID\":{\"a\":4},\"PROD_ID\":210.47,\"USER_NAME\":\"user4\"},"
                + "{\"USER_ID\":340,\"CUST_ID\":{\"a\":5},\"PROD_ID\":210.48,\"USER_NAME\":\"user5\"},"
                + "{\"USER_ID\":350,\"CUST_ID\":{\"a\":6},\"PROD_ID\":210.49,\"USER_NAME\":\"user6\"},"
                + "{\"USER_ID\":360,\"CUST_ID\":{\"a\":7},\"PROD_ID\":210.40,\"USER_NAME\":\"user7\"}]";

        String json2="[{\"CUST_ID\":{\"a\":1},\"PROD_ID\":23.56,\"USER_ID\":300,\"USER_NAME\":\"user1\"},"
                + "{\"USER_ID\":0,\"CUST_ID\":{\"a\":4},\"PROD_ID\":210.45,\"USER_NAME\":\"user2\"},"
                + "{\"USER_ID\":115,\"CUST_ID\":{\"a\":115},\"PROD_ID\":210.46,\"USER_NAME\":\"user3\"},"
                + "{\"USER_ID\":330,\"CUST_ID\":{\"a\":4},\"PROD_ID\":210.47,\"USER_NAME\":\"user4\"},"
                + "{\"USER_ID\":340,\"CUST_ID\":{\"a\":5},\"PROD_ID\":210.48,\"USER_NAME\":\"user5\"},"
                + "{\"USER_ID\":350,\"CUST_ID\":{\"a\":6},\"PROD_ID\":210.49,\"USER_NAME\":\"user6\"},"
                + "{\"USER_ID\":360,\"CUST_ID\":{\"a\":7},\"PROD_ID\":210.40,\"USER_NAME\":\"user7\"}]";

        Statement statement = connection.createStatement();


        ResultSet resultSet = null;
        long begin = System.currentTimeMillis();

        rootSchema.add("abc", new JsonSchema("test", json));
        resultSet = statement.executeQuery(
                "select * from \"abc" + "\".\"test\" where USER_ID>0 ");

        long mid = System.currentTimeMillis();
        System.out.println("query:" + (System.currentTimeMillis() - begin));

        rootSchema.add("abc", new JsonSchema("test", json2));
        resultSet = statement.executeQuery(
                "select * from \"abc" + "\".\"test\" where USER_ID=115 ");
        System.out.println("query:" + (System.currentTimeMillis() - mid));

        while (resultSet.next()) {
            JSONObject jo = new JSONObject();
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                jo.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
            }
            System.out.println(jo.toJSONString());
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
