package tech.xixing.design.pattern.creational.singleton;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 8:45
 */
public class LazySingleton {
    private static LazySingleton lazySingleton=null;

    private static boolean flag=true;

    private LazySingleton(){
        if(!flag){
           throw new RuntimeException("单例模式禁止反射");
        }else {
            flag=false;
        }

    }

    /**
     * 如果锁的是静态方法，相当于把这个class给锁了
     * 锁的不是静态方法，相当于把堆内存中的实例锁了
     * @return
     */
    public synchronized static LazySingleton getInstance(){
        if(lazySingleton==null){
            lazySingleton=new LazySingleton();
        }
        return lazySingleton;
//        synchronized (LazySingleton.class){
//            if(lazySingleton==null){
//                lazySingleton=new LazySingleton();
//            }
//        }
//        return lazySingleton;
    }


}
