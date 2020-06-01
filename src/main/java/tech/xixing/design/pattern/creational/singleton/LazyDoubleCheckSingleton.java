package tech.xixing.design.pattern.creational.singleton;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 8:45
 */
public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton lazySingleton=null;

    private LazyDoubleCheckSingleton(){

    }

    /**
     * 如果锁的是静态方法，相当于把这个class给锁了
     * 锁的不是静态方法，相当于把堆内存中的实例锁了
     * @return
     */
    public  static LazyDoubleCheckSingleton getInstance(){
        if(lazySingleton==null){
            synchronized (LazyDoubleCheckSingleton.class){
                if(lazySingleton==null){
                    lazySingleton=new LazyDoubleCheckSingleton();
                    //1.分配内存给这个对象
                    //2.初始化对象
                    //3.设置lazyDoubleCheckSingleton 指向刚分配的内存地址
                    //2和3可能顺序会颠倒，加入volatile会使得2和3只能按顺序，使得线程安全，
                    // 可以看到共享内存的状态


                }
            }

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
