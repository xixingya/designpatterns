package tech.xixing.design.pattern.creational.singleton;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 8:48
 */
public class T implements Runnable {
    @Override
    public void run() {
        /*LazySingleton lazySingleton=LazySingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+" "+lazySingleton);*/

//        StaticInnerClassSingleton staticInnerClassSingleton=StaticInnerClassSingleton.getInstance();
//        ContainerSingleton.putInstance("object",new Object());
//        Object object = ContainerSingleton.getInstance("object");

        ThreadLocalInstance threadLocalInstance=ThreadLocalInstance.getInstance();
        System.out.println(Thread.currentThread().getName()+" "+threadLocalInstance);


    }
}
