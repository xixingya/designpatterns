package tech.xixing.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhifei
 * @date 2022/7/22 2:30 下午
 */
public class TestFullName {

    public static void main(String[] args) {
        Map<String,Object> input =new HashMap<>();

        Date date =new Date();

        Expression expr = AviatorEvaluator.getInstance().compile("aaa = date_to_string(sysdate(),'yyyyMMdd')", true);
        expr.execute(input);
        System.out.println(input);

    }
}
