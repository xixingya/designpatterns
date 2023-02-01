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
import java.util.LinkedHashMap;

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

        String json = "[{\n" +
                "  \"roomTag\": \"超级王牌\",\n" +
                "  \"uid\": \"14322342343\",\n" +
                "  \"roomStatus\": \"1\",\n" +
                "  \"catId\": \"412592068762796032\",\n" +
                "  \"catTeamDesc\": \"不限/娱乐局/不限/四排\",\n" +
                "  \"catName\": \"和平精英\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672748524185\",\n" +
                "  \"roomTitle\": \"猛男甜妹多声线 三保一车队\uD83D\uDCB0可双排\",\n" +
                "  \"teamChatId\": \"0\",\n" +
                "  \"roomId\": \"sfdsfdsfdsfds\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "}]";

        String json2="[{\n" +
                "  \"uid\": \"343431434\",\n" +
                "  \"roomStatus\": \"0\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672751295656\",\n" +
                "  \"roomId\": \"fsfdsfvxcv\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "}]";



        ResultSet resultSet = null;
        String sql = "select * from abc" + ".test where sourceFrom = 0 and appId = 10 and catTeamDesc = '不限/娱乐局/不限/四排'";
        System.out.println(sql);
        // 需要固定字段位置，以免缺少的情况会导致PreparedStatement报错。
        LinkedHashMap<String,Object> fields = new LinkedHashMap<>();
        fields.put("uid",String.class);
        fields.put("roomTag",String.class);
        fields.put("roomStatus",String.class);
        fields.put("catId",String.class);
        fields.put("catTeamDesc",String.class);
        fields.put("catName",String.class);
        fields.put("appId",String.class);
        fields.put("sourceFrom",String.class);
        fields.put("roomId",String.class);
        JsonSchema test = new JsonSchema("test", json,fields);
        rootSchema.add("abc", test);
        rootSchema.add("my_func", ScalarFunctionImpl.create(TestUdf.class,"add1"));
        PreparedStatement statement = connection.prepareStatement(sql);

        //RelRoot relRoot = genRelRoot(connection, sql);
        //PreparedStatement statement = preparedStatement(optiqConnection, relRoot.rel);

        long begin = System.currentTimeMillis();
        resultSet = statement.executeQuery();
        System.out.println("query:" + (System.currentTimeMillis() - begin));
        long mid = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            test.setTarget(json2);
            resultSet = statement.executeQuery();
        }
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
