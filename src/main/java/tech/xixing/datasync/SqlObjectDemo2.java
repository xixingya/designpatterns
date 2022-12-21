package tech.xixing.datasync;


import java.sql.*;
import java.util.Properties;

import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.jdbc.CalcitePrepare;
import org.apache.calcite.model.JsonFunction;
import org.apache.calcite.model.JsonMapSchema;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.schema.SchemaPlus;

import com.alibaba.fastjson.JSONObject;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import org.apache.calcite.schema.impl.TableFunctionImpl;
import org.apache.calcite.server.CalciteServerStatement;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlJsonValueFunction;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelRunner;
import tech.xixing.datasync.adapter.JsonSchema;
import tech.xixing.datasync.udf.TestUdf;

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

    public static void run() throws Exception {
        Properties properties = new Properties();
        // 需要添加这个去除大小写，要不然自定义的udf会被转成大写从而报没有这个函数的错误
        properties.setProperty("caseSensitive","false");
        Connection connection = DriverManager.getConnection("jdbc:calcite:",properties);
        CalciteConnection optiqConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = optiqConnection.getRootSchema();

        String json = "[{\"CUST_ID\":{\"a\":1},\"PROD_ID\":23.56,\"USER_ID\":300,\"USER_NAME\":\"user1\"},"
                + "{\"USER_ID\":310,\"CUST_ID\":{\"a\":2},\"PROD_ID\":210.45,\"USER_NAME\":\"user2\"},"
                + "{\"USER_ID\":320,\"CUST_ID\":{\"a\":3},\"PROD_ID\":210.46,\"USER_NAME\":\"user3\"},"
                + "{\"USER_ID\":330,\"CUST_ID\":{\"a\":4},\"PROD_ID\":210.47,\"USER_NAME\":\"user4\"},"
                + "{\"USER_ID\":340,\"CUST_ID\":{\"a\":5},\"PROD_ID\":210.48,\"USER_NAME\":\"user5\"},"
                + "{\"USER_ID\":350,\"CUST_ID\":{\"a\":6},\"PROD_ID\":210.49,\"USER_NAME\":\"user6\"},"
                + "{\"USER_ID\":360,\"CUST_ID\":{\"a\":7},\"PROD_ID\":210.40,\"USER_NAME\":\"user7\"}]";

        String json2="[{\"CUST_ID\":{\"a\":1},\"PROD_ID\":23.56,\"USER_ID\":1,\"USER_NAME\":\"user1\"},"
                + "{\"USER_ID\":0,\"CUST_ID\":{\"a\":4},\"PROD_ID\":210.45,\"USER_NAME\":\"user2\"},"
                + "{\"USER_ID\":115,\"CUST_ID\":{\"a\":115},\"PROD_ID\":210.46,\"USER_NAME\":\"user3\"},"
                + "{\"USER_ID\":330,\"CUST_ID\":{\"a\":4},\"PROD_ID\":210.47,\"USER_NAME\":\"user4\"},"
                + "{\"USER_ID\":340,\"CUST_ID\":{\"a\":5},\"PROD_ID\":210.48,\"USER_NAME\":\"user5\"},"
                + "{\"USER_ID\":350,\"CUST_ID\":{\"a\":6},\"PROD_ID\":210.49,\"USER_NAME\":\"user6\"},"
                + "{\"USER_ID\":360,\"CUST_ID\":{\"a\":7},\"PROD_ID\":210.40,\"USER_NAME\":\"user7\"}]";



        ResultSet resultSet = null;
        long begin = System.currentTimeMillis();
        String sql = "select * from(select JSON_VALUE(CUST_ID,'$.a') as cid, my_func(1) as myres from abc" + ".test where JSON_VALUE(CUST_ID,'$.a')>6) where CID= 7 ";
        System.out.println(sql);
        JsonSchema test = new JsonSchema("test", json);
        rootSchema.add("abc", test);
        rootSchema.add("my_func", ScalarFunctionImpl.create(TestUdf.class,"add1"));
        PreparedStatement statement = connection.prepareStatement(sql);

        //RelRoot relRoot = genRelRoot(connection, sql);
        //PreparedStatement statement = preparedStatement(optiqConnection, relRoot.rel);

        resultSet = statement.executeQuery();

        System.out.println("query:" + (System.currentTimeMillis() - begin));
        long mid = System.currentTimeMillis();
        test.setTarget(json2);
        resultSet = statement.executeQuery();
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

    /**
     * Planner解析，校验，然后生成RelNode，使用mysql的sql语法格式
     *
     * @param connection
     * @param sql
     * @return
     * @throws Exception 参考自：https://zhuanlan.zhihu.com/p/65345335
     */
    public static RelRoot genRelRoot(Connection connection, String sql) throws Exception {
        //从 conn 中获取相关的环境和配置，生成对应配置
        CalciteServerStatement st = connection.createStatement().unwrap(CalciteServerStatement.class);
        CalcitePrepare.Context prepareContext = st.createPrepareContext();
        final FrameworkConfig config = Frameworks.newConfigBuilder()
                .parserConfig(SqlParser.configBuilder().setLex(Lex.MYSQL).build())
                .defaultSchema(prepareContext.getRootSchema().plus())
//                .traitDefs(ConventionTraitDef.INSTANCE, RelDistributionTraitDef.INSTANCE)
                .build();
        Planner planner = Frameworks.getPlanner(config);
        RelRoot root = null;
        try {
            SqlNode parse1 = planner.parse(sql);
            SqlNode validate = planner.validate(parse1);
            root = planner.rel(validate);
            RelNode rel = root.rel;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    public static PreparedStatement preparedStatement(Connection connection,RelNode rel) throws SQLException {
        RelRunner relRunner = connection.unwrap(RelRunner.class);
        return relRunner.prepareStatement(rel);
    }
}
