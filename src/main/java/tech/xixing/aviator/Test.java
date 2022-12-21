package tech.xixing.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.*;

public class Test {
    public static void main2(String[] args) {
        long current =System.currentTimeMillis();
        //AviatorEvaluator.getInstance().setCachedExpressionByDefault(true);
        for (int i = 0; i < 1; i++) {
            Map<String,Object> input =new HashMap<>();
            input.put("uid",123);
            ArrayList<Map> nums = new ArrayList<>();
            nums.add(Collections.singletonMap("uid","qaq"));
            input.put("nums",nums);
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();

            objectObjectHashMap.put("value","qaq");

            input.put("roomId",objectObjectHashMap);
            AviatorEvaluator.compile("aaa");
            //使用不带getInstance会使得全局开启缓存失效
            Object execute = AviatorEvaluator.getInstance().execute("#nums[0].uid", input,true);
            System.out.println(execute);
        }
        System.out.println(System.currentTimeMillis()-current);
    }

    public static void main(String[] args) {
        Map<String,Object> input =new HashMap<>();
        input.put("uid",123);
        ArrayList<Map> nums = new ArrayList<>();
        nums.add(Collections.singletonMap("uid","qaq"));
        input.put("nums",nums);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();

        objectObjectHashMap.put("value","qaq");

        input.put("roomId",objectObjectHashMap);
        input.put("user",TestEnum.MOBILE_ADULT);
        input.put("currentTime","2022-07-12 12:23:00");

        Expression compile = AviatorEvaluator.compile("value = str(user)");
        long start = System.currentTimeMillis();
        compile.execute(input);
        AviatorEvaluator.execute("asd = qaq.value!='10'",input);
        System.out.println("query:"+(System.currentTimeMillis()-start));
        //System.out.println(compile.getSourceFile());
    }
}
