package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSONObject;
import org.apache.calcite.sql.parser.SqlParseException;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import tech.xixing.datasync.anno.EnableCustomUdf;
import tech.xixing.datasync.config.SQLConfig;
import tech.xixing.datasync.udf.AviatorUdf;

/**
 * @author liuzhifei
 * @date 2023/1/11 10:56 上午
 */
@EnableCustomUdf(scanPackage = "tech.xixing.datasync.udf")
class SQLTransformerTest {

    @Test
    void testCommon() throws SQLException, SqlParseException {
        String sql = "select * from kafka" + ".test where sourceFrom = 0 and appId = 10";
        transform(sql,true);
    }

    @Test
    public static void transform(String sql,boolean isNew) throws SQLException, SqlParseException {
        String table = "test";
        // 需要固定字段位置，以免缺少的情况会导致PreparedStatement报错。
//        LinkedHashMap<String,Class> fields = new LinkedHashMap<>();
//        fields.put("uid",String.class);
//        fields.put("roomTag",String.class);
//        fields.put("roomStatus",String.class);
//        fields.put("catId",String.class);
//        fields.put("catTeamDesc",String.class);
//        fields.put("catName",String.class);
//        fields.put("appId",String.class);
//        fields.put("sourceFrom",String.class);
//        fields.put("roomId",String.class);
//        fields.put("ext",String.class);
        LinkedHashMap<String, Object> fields = null;
        fields = SQLUtils.getFieldsByJSONObject("{\n" +
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
                "  }");

        if(isNew){
            String table1 = SQLUtils.transformSqlByJsonObj("{\n" +
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
                    "  }", "test");
            fields = SQLUtils.getTableByCreateSql(table1);
        }
        SQLConfig sqlConfig = new SQLConfig(sql, table, fields);
        SQLTransformer sqlTransformer = new SQLTransformer(sqlConfig);
        sqlTransformer.registerUdf("aviator_func", AviatorUdf.class);
        long l = System.currentTimeMillis();
        List<JSONObject> res = sqlTransformer.transform("[\n" +
                "  {\n" +
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
                "  },\n" +
                "  {\n" +
                "    \"roomTag\": \"\",\n" +
                "    \"ext\": {\n" +
                "      \"status\": 1\n" +
                "    },\n" +
                "    \"list\": [{\"name\": \"qaq1\",\"desc\": \"hello\"},{\"name\": \"wang1\",\"desc\": \"world\"},{\"desc\": \"good\",\"name\": \"li1\"}],\n" +
                "    \"catTeamDesc\": \"不限/娱乐局/不限/四排\",\n" +
                "    \"teamChatId\": \"0\",\n" +
                "    \"roomId\": \"2240b926d30d4431bb9f9cb92fdd568b\",\n" +
                "    \"uid\": \"558\",\n" +
                "    \"roomStatus\": \"1\",\n" +
                "    \"catId\": \"412592068762796032\",\n" +
                "    \"catName\": \"和平精英\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672750860186\",\n" +
                "    \"roomTitle\": \"\uD83C\uDF38王者吃\uD83D\uDC14战神哥车队\uD83D\uDCB0\uD83D\uDCB0双区有号\",\n" +
                "    \"sourceFrom\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"ext\": {\n" +
                "      \"status\": 0\n" +
                "    },\n" +
                "    \"list\": [{\"name\": \"qaq2\",\"desc\": \"hello\"},{\"name\": \"wang2\",\"desc\": \"world\"},{\"desc\": \"good\",\"name\": \"li2\"}],\n" +
                "    \"roomId\": \"4b0cc8c45f7c47d0a7084e420736f035\",\n" +
                "    \"uid\": \"353453\",\n" +
                "    \"roomStatus\": \"0\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672750499070\",\n" +
                "    \"sourceFrom\": \"0\"\n" +
                "  }\n" +
                "]");
        System.out.println("use time =" + (System.currentTimeMillis()-l));
        System.out.println(res);

        long l2 = System.currentTimeMillis();

        List<JSONObject> res2 = sqlTransformer.transform("[\n" +
                "  {\n" +
                "    \"roomId\": \"faqf24vs\",\n" +
                "    \"uid\": \"23461\",\n" +
                "    \"roomStatus\": \"0\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672750357647\",\n" +
                "    \"sourceFrom\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"roomTag\": \"荣耀王者\",\n" +
                "    \"catTeamDesc\": \"不限/娱乐局/不限/五排\",\n" +
                "    \"teamChatId\": \"0\",\n" +
                "    \"roomId\": \"fsfw23rg\",\n" +
                "    \"uid\": \"43232\",\n" +
                "    \"roomStatus\": \"1\",\n" +
                "    \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "    \"catName\": \"王者荣耀\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672742937373\",\n" +
                "    \"roomTitle\": \"我无敌你随意百段打野带飞\uD83D\uDCB0\",\n" +
                "    \"sourceFrom\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"roomTag\": \"荣耀王者\",\n" +
                "    \"catTeamDesc\": \"不限/上分局/不限/五排\",\n" +
                "    \"teamChatId\": \"0\",\n" +
                "    \"roomId\": \"fsafd3\",\n" +
                "    \"uid\": \"5331\",\n" +
                "    \"roomStatus\": \"1\",\n" +
                "    \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "    \"catName\": \"王者荣耀\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672749937699\",\n" +
                "    \"roomTitle\": \"张sir国服车队上分速来\",\n" +
                "    \"sourceFrom\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"roomId\": \"fsfsf3fg3\",\n" +
                "    \"uid\": \"23421\",\n" +
                "    \"roomStatus\": \"0\",\n" +
                "    \"appId\": \"10\",\n" +
                "    \"varTimeStamp\": \"1672749361134\",\n" +
                "    \"sourceFrom\": \"0\",\n" +
                "    \"ext\": {\n" +
                "      \"status\": 1\n" +
                "    }\n" +
                "  }\n" +
                "]\n");

        System.out.println("use time ="+(System.currentTimeMillis()-l2));
        System.out.println(res2);
    }
    @Test
    void testUtil(){
        Map fieldsByJSONObject = SQLUtils.getFieldsByJSONObject("{\n" +
                "  \"roomTag\": \"荣耀王者\",\n" +
                "  \"uid\": \"5452234\",\n" +
                "  \"roomStatus\": \"1\",\n" +
                "  \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "  \"catTeamDesc\": \"不限/上分局/不限/五排\",\n" +
                "  \"catName\": \"王者荣耀\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672749937699\",\n" +
                "  \"roomTitle\": \"张sir国服车队上分速来\",\n" +
                "  \"teamChatId\": \"0\",\n" +
                "  \"roomId\": \"9a5697c3a0d04facb131a2c944f3268a\",\n" +
                "  \"sourceFrom\": \"0\",\n" +
                "  \"ext\": {\n" +
                "    \"status\": 0\n" +
                "  }\n" +
                "}");

        JSONObject object = new JSONObject(fieldsByJSONObject);
        System.out.println(object);
        System.out.println();
    }

    @Test
    void testJsonFormat() throws SQLException, SqlParseException {

        String sql = "select `sourceFrom`,JSON_VALUE(ext,'$.status') as status from kafka.test where sourceFrom = 0 and appId = 10 and JSON_VALUE(ext,'$.status')=1";
        String sql2 = "select * from kafka.test";
        transform(sql2,true);
       // System.out.println(JSON.class.isAssignableFrom(JSONObject.class));

    }

    @Test
    void testSqlChange() throws SqlParseException {
        String sql = "select * from kafka.aaa.bbb where uid <> 0";
        String s = SQLUtils.changeSQL2StandardCalciteSQL(sql);
        // transform(s);
        System.out.println(s);
    }

    @Test
    void testAviatorUdf() throws Exception{
        String sql = "select uid, `sourceFrom`,aviator_func(ext,'seq.map(\"uid\",123,\"status\",status)') as temp from kafka.test where sourceFrom = 0";
        transform(sql,false);
    }

    @Test
    void testUdtf()throws Exception{
        String sql = "select cat, uid, `sourceFrom`,aviator_func(ext,'seq.map(\"uid\",123,\"status\",status)') as temp ,JSON_VALUE(ext,'$.status') as status from kafka.test left join lateral table(test_split(`catTeamDesc`,'/')) as t(cat) on true";
        transform(sql,false);
    }

    @Test
    void testUnnest()throws Exception{
        String sqlSe = "select type,roomTag from kafka.test left join unnest(`list`) as t(type) on true";
        String sql = "create table a(roomTag string,ext MAP<STRING,STRING>, list ARRAY<MAP<STRING,STRING>>)";
        String table = "test";
        LinkedHashMap<String, Object> fields = SQLUtils.getTableByCreateSql(sql);

        SQLConfig sqlConfig = new SQLConfig(sqlSe, table, fields);

        SQLTransformer sqlTransformer = new SQLTransformer(sqlConfig);

        List<JSONObject> transform = sqlTransformer.transform("[{\"roomTag\":\"\",\"ext\":{\"status\":1,\"f1\":\"10086\"},\"catTeamDesc\":\"不限/娱乐局/不限/四排\",\"list\":[{\"name\":\"qaq1\",\"desc\":\"hello\"},{\"name\":\"wang1\",\"desc\":\"world\"},{\"name\":\"li1\",\"desc\":\"good\"}],\"teamChatId\":\"0\",\"roomId\":\"2240b926d30d4431bb9f9cb92fdd568b\",\"uid\":\"558\",\"roomStatus\":\"1\",\"catId\":\"412592068762796032\",\"catName\":\"和平精英\",\"appId\":\"10\",\"varTimeStamp\":\"1672750860186\",\"roomTitle\":\"\uD83C\uDF38王者吃\uD83D\uDC14战神哥车队\uD83D\uDCB0\uD83D\uDCB0双区有号\",\"sourceFrom\":\"0\"}]");

        System.out.println(transform);
    }

    @Test
    void testCreate()throws Exception{
        String sql = "create table\n" +
                "  YPP_PROFILE_OUT (\n" +
                "    bizType string,\n" +
                "    dataType string,\n" +
                "    id string,\n" +
                "    kv MAP < string,\n" +
                "    string >\n" +
                "  )";
        String table = "YPP_PROFILE_OUT";
        LinkedHashMap<String, Object> fields = SQLUtils.getTableByCreateSql(sql);

        long l = System.currentTimeMillis();
        SQLConfig sqlConfig = new SQLConfig("select t1.*, t2.*  from (select id as uid, kv['mvp_home_view'] as sql_mvp_home_view from kafka.YPP_PROFILE_OUT where bizType = 'ORDER' and dataType = 'targetUid' and kv['mvp_home_view'] is not null and kv['mvp_home_view']>2) as t1 right join unnest(dubbo_talent_cat(t1.uid)) as t2(cat_id,label_ids) on true", table, fields);

        System.out.println("useTime="+(System.currentTimeMillis()-l));
        SQLTransformer sqlTransformer = new SQLTransformer(sqlConfig);

        List<JSONObject> transform = sqlTransformer.transform("[{\n" +
                "  \"bizType\": \"ORDER\",\n" +
                "  \"dataType\": \"targetUid\",\n" +
                "  \"id\": \"12300\",\n" +
                "  \"kv\": {\n" +
                "    \"mvp_home_view\": \"3\"\n" +
                "  },\n" +
                "  \"ts\": \"1676254860000\"\n" +
                "}]");

        System.out.println(transform);
        // sqlConfig.execute("[{\"roomTag\":\"\",\"ext\":{\"status\":1},\"catTeamDesc\":\"不限/娱乐局/不限/四排\",\"list\":[{\"name\":\"qaq1\",\"desc\":\"hello\"},{\"name\":\"wang1\",\"desc\":\"world\"},{\"name\":\"li1\",\"desc\":\"good\"}],\"teamChatId\":\"0\",\"roomId\":\"2240b926d30d4431bb9f9cb92fdd568b\",\"uid\":\"558\",\"roomStatus\":\"1\",\"catId\":\"412592068762796032\",\"catName\":\"和平精英\",\"appId\":\"10\",\"varTimeStamp\":\"1672750860186\",\"roomTitle\":\"\uD83C\uDF38王者吃\uD83D\uDC14战神哥车队\uD83D\uDCB0\uD83D\uDCB0双区有号\",\"sourceFrom\":\"0\"}]");

    }

}