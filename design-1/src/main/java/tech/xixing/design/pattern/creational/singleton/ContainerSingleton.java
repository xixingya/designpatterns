package tech.xixing.design.pattern.creational.singleton;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 14:53
 */
public class ContainerSingleton {

    private static Map<String,Object> containerMap=new HashMap<>();

    private ContainerSingleton(){

    }
    public static void putInstance(String key,Object instance){
        if(!StringUtils.isEmpty(key)&&instance!=null){
            if(!containerMap.containsKey(key)){
                containerMap.put(key,instance);
            }
        }
    }

    public static Object getInstance(String key){
        return containerMap.get(key);
    }

}
