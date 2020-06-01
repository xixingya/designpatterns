package tech.xixing.design.pattern.creational.singleton;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 15:39
 */
public class ThreadLocalInstance {

    private final static ThreadLocal<ThreadLocalInstance> threadLocalInstance=
            new ThreadLocal<ThreadLocalInstance>(){
                @Override
                protected ThreadLocalInstance initialValue() {
                    return new ThreadLocalInstance();
                }
            };
    private ThreadLocalInstance(){

    }

    public static ThreadLocalInstance getInstance(){
        return threadLocalInstance.get();
    }



}
