package tech.xixing.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhifei
 * @date 2022/8/30 2:31 下午
 */
public class TestReg {
    public static void main(String[] args) {
        String str = "#明天见#";
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("nickname",str);

        System.out.println(AviatorEvaluator.execute("a = string.replace_all(nickname,'[^\\\\p{L}\\\\p{M}\\\\p{N}\\\\p{P}\\\\p{Z}\\\\p{Cf}\\\\p{Cs}\\\\s]','')",hashMap));
        System.out.print(str.replaceAll("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]",""));
    }
}
