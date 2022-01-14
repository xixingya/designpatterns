package tech.xixing.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        long current =System.currentTimeMillis();
        AviatorEvaluator.getInstance().setCachedExpressionByDefault(true);
        for (int i = 0; i < 10000000; i++) {
            Map<String,Object> input =new HashMap<>();
            input.put("uid",123);
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("value","qaq");
            input.put("roomId",objectObjectHashMap);
            Object execute = AviatorEvaluator.execute("roomId.value", input);
            //System.out.println(execute);
        }
        System.out.println(System.currentTimeMillis()-current);
    }
}
