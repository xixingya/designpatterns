package tech.xixing.design.pattern.creational.singleton;

import java.io.Serializable;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 10:38
 */
public class HungrySingleton implements Serializable {
    //private final static HungrySingleton hungrySingleton=new HungrySingleton();

    private final static HungrySingleton hungrySingleton;
    static {
        hungrySingleton=new HungrySingleton();
    }
    private HungrySingleton(){
        if(hungrySingleton!=null){
            throw new RuntimeException("单例模式禁止反射");
        }

    }

    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }

    private Object readResolve(){
        return hungrySingleton;
    }


}
