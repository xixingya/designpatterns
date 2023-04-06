package tech.xixing.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sheyang
 * @date 2022/11/1 14:45
 */
public class ObjectUtil {

    public static Map<String, String> stringToMap(String data) {
        return stringToMap(data, ",");
    }

    public static Map<String, String> stringToMap(String data, String split) {
        data = data.substring(1, data.length() - 1);
        String[] items = data.split(split);
        Map<String, String> resultMap = new HashMap<>();
        for (String item : items) {
            String[] b = item.split("=");
            resultMap.put(b[0], b[1]);
        }
        return resultMap;
    }

}
