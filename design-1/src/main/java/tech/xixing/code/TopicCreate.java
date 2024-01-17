package tech.xixing.code;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liuzhifei
 * @date 2022/9/15 3:42 下午
 */
public class TopicCreate {

    public static void main(String[] args) {
        String s ="";

        JSONArray jsonArray = new JSONArray();
        String[] ones = s.split("\n");
        for (String one : ones) {
            String[] split = one.split("\t");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("topic",split[0]);
            jsonObject.put("cluster","bigdata");
            jsonObject.put("partition",split[4]);
            jsonObject.put("appId",split[8]);
            if(split.length>10){
                jsonObject.put("remark",split[10]);
            }
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray.size());
        System.out.println(jsonArray.toJSONString());
    }
}
