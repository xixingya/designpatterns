package tech.xixing.design.pattern.creational.singleton;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 10:23
 */
public class StaticInnerClassSingleton {

    private static class InnerClass{
        private static StaticInnerClassSingleton staticInnerClassSingleton=new StaticInnerClassSingleton();

    }
    private StaticInnerClassSingleton(){
        if(InnerClass.staticInnerClassSingleton!=null){
            throw new RuntimeException("单例模式禁止反射");
        }

    }

    public static StaticInnerClassSingleton getInstance(){
        return InnerClass.staticInnerClassSingleton;
    }
}
