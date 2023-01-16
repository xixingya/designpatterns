package tech.xixing.datasync.udf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.aviator.AviatorEvaluator;
import tech.xixing.datasync.anno.Udf;

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
}
