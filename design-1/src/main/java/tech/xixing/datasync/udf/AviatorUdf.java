package tech.xixing.datasync.udf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.aviator.AviatorEvaluator;
import tech.xixing.datasync.anno.Udf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhifei
 * @date 2023/1/11 4:53 下午
 */
public class AviatorUdf {

    @Udf(name = "aviator_func")
    public static Object execute(String jsonStr,String expression){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return AviatorEvaluator.execute(expression, jsonObject, true);
    }

    @Udf(name = "aviator_func")
    public static Object execute2(Map map, String expression){
        return AviatorEvaluator.execute(expression, map, true);
    }

    @Udf(name = "dubbo_talent_cat")
    public static Map execute3(Long uid){
        Map map = new HashMap();
        map.put("11","22");
        map.put("33","44");
        return map;
    }


}
