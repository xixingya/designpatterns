package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSONObject;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author liuzhifei
 * @date 2023/1/11 10:56 上午
 */
class SQLTransformerTest {

    @org.junit.jupiter.api.Test
    void transform() throws SQLException {
        String sql = "select * from kafka" + ".test where sourceFrom = 0 and appId = 10";
        String table = "test";
        // 需要固定字段位置，以免缺少的情况会导致PreparedStatement报错。
        LinkedHashMap<String,Class> fields = new LinkedHashMap<>();
        fields.put("uid",String.class);
        fields.put("roomTag",String.class);
        fields.put("roomStatus",String.class);
        fields.put("catId",String.class);
        fields.put("catTeamDesc",String.class);
        fields.put("catName",String.class);
        fields.put("appId",String.class);
        fields.put("sourceFrom",String.class);
        fields.put("roomId",String.class);

        SQLConfig sqlConfig = new SQLConfig(sql, table, fields);
        SQLTransformer sqlTransformer = new SQLTransformer(sqlConfig);
        long l = System.currentTimeMillis();
        List<JSONObject> res = sqlTransformer.transform("[{\n" +
                "  \"roomTag\": \"传奇王者\",\n" +
                "  \"uid\": \"223\",\n" +
                "  \"roomStatus\": \"1\",\n" +
                "  \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "  \"catTeamDesc\": \"不限/娱乐局/不限/五排\",\n" +
                "  \"catName\": \"王者荣耀\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672742306444\",\n" +
                "  \"roomTitle\": \"巅峰2500 技术 98胜 车队\",\n" +
                "  \"teamChatId\": \"0\",\n" +
                "  \"roomId\": \"0167944df1424738a34051940268d17c\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "},{\n" +
                "\"roomTag\": \"\",\n" +
                "\"uid\": \"558\",\n" +
                "\"roomStatus\": \"1\",\n" +
                "\"catId\": \"412592068762796032\",\n" +
                "\"catTeamDesc\": \"不限/娱乐局/不限/四排\",\n" +
                "\"catName\": \"和平精英\",\n" +
                "\"appId\": \"10\",\n" +
                "\"varTimeStamp\": \"1672750860186\",\n" +
                "\"roomTitle\": \"\uD83C\uDF38王者吃\uD83D\uDC14战神哥车队\uD83D\uDCB0\uD83D\uDCB0双区有号\",\n" +
                "\"teamChatId\": \"0\",\n" +
                "\"roomId\": \"2240b926d30d4431bb9f9cb92fdd568b\",\n" +
                "\"sourceFrom\": \"0\"\n" +
                "},{\n" +
                "\"uid\": \"211121005053150209\",\n" +
                "\"roomStatus\": \"0\",\n" +
                "\"appId\": \"10\",\n" +
                "\"varTimeStamp\": \"1672750499070\",\n" +
                "\"roomId\": \"4b0cc8c45f7c47d0a7084e420736f035\",\n" +
                "\"sourceFrom\": \"0\"\n" +
                "}]");
        System.out.println("use time ="+(System.currentTimeMillis()-l));
        System.out.println(res);

        long l2 = System.currentTimeMillis();

        List<JSONObject> res2 = sqlTransformer.transform("[{\n" +
                "  \"uid\": \"23461\",\n" +
                "  \"roomStatus\": \"0\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672750357647\",\n" +
                "  \"roomId\": \"faqf24vs\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "},{\n" +
                "  \"roomTag\": \"荣耀王者\",\n" +
                "  \"uid\": \"43232\",\n" +
                "  \"roomStatus\": \"1\",\n" +
                "  \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "  \"catTeamDesc\": \"不限/娱乐局/不限/五排\",\n" +
                "  \"catName\": \"王者荣耀\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672742937373\",\n" +
                "  \"roomTitle\": \"我无敌你随意百段打野带飞\uD83D\uDCB0\",\n" +
                "  \"teamChatId\": \"0\",\n" +
                "  \"roomId\": \"fsfw23rg\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "},{\n" +
                "  \"roomTag\": \"荣耀王者\",\n" +
                "  \"uid\": \"5331\",\n" +
                "  \"roomStatus\": \"1\",\n" +
                "  \"catId\": \"8efb76c4477637c4c70352b8ce2be686\",\n" +
                "  \"catTeamDesc\": \"不限/上分局/不限/五排\",\n" +
                "  \"catName\": \"王者荣耀\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672749937699\",\n" +
                "  \"roomTitle\": \"张sir国服车队上分速来\",\n" +
                "  \"teamChatId\": \"0\",\n" +
                "  \"roomId\": \"fsafd3\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "},{\n" +
                "  \"uid\": \"23421\",\n" +
                "  \"roomStatus\": \"0\",\n" +
                "  \"appId\": \"10\",\n" +
                "  \"varTimeStamp\": \"1672749361134\",\n" +
                "  \"roomId\": \"fsfsf3fg3\",\n" +
                "  \"sourceFrom\": \"0\"\n" +
                "}]");

        System.out.println("use time ="+(System.currentTimeMillis()-l2));
        System.out.println(res2);
    }
}