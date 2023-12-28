package tech.xixing.proxy.dymatic.bytebuddy;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class Demo3 {


    /**
     * 接口实现
     */
    public static class Call extends AbLogger{

        public String name;

        public String call1(String ok) {
            System.out.println(" call 1 ");
            return "";
        }

        public String getName(){
            return name;
        }

        public void call2() {
            System.out.println(" call 2 ");
        }
    }


    public static void main(String[] args) throws Exception {
        Logger logger = new Logger("aa");
        Logger.aaa =true;
        logger.log();
        Call proxy = createByteBuddyDynamicProxy();
        proxy.call2();
        //proxy.call2();
    }

    private static Call createByteBuddyDynamicProxy() throws Exception {
        DynamicType.Loaded<Call> proxy = new ByteBuddy().subclass(Call.class)
                .defineField("logger",Logger.class,net.bytebuddy.description.modifier.Visibility.PUBLIC)
                .method(ElementMatchers.isDeclaredBy(Call.class).or(ElementMatchers.isDeclaredBy(AbLogger.class)))
                .intercept(MethodDelegation.to(MethodInterceptor.class))
                .make()
                .load(Demo2.class.getClassLoader());

        // 保存到磁盘 用于测试分析，开发使用的使用不需要使用
        // proxy.saveIn(new File("./so/"));
        // proxy.getLoaded().getDeclaredConstructor(String.class).newInstance("a");
        Call call =  (Call)proxy.getLoaded().getDeclaredConstructor().newInstance();
        call.getClass().getDeclaredField("logger").set(call,new Logger("aa"));

        return call;
    }

    public static class MethodInterceptor {

//        @RuntimeType
//        //这里使用的Super 注解，调用父类的方法
//        public static Object interceptor(@Super Call instance , @Origin Method method , @AllArguments Object[] args) throws Exception {
//            System.out.println("bytebuddy method call before  ");
//            Object result = method.invoke(instance, args);
//            System.out.println(instance.getClass().getName());
//            // instance.call2();
//            System.out.println("bytebuddy method call after   ");
//            return result;
//        }


//        @Advice.OnMethodEnter
//        public static int aaa(@Advice.FieldValue(value = "name",declaringType = Call.class) String name){
//            System.out.println("name,"+name);
//            return 0;
//        }

        @RuntimeType
        // 这里使用的Super 注解，调用父类的方法
        public static Object interceptor(@FieldValue("logger") Logger lg,@This Object instance, @SuperCall Callable<?> callable , @Origin Method method , @AllArguments Object[] args) throws Exception {
            System.out.println("bytebuddy method call before  ");
            // System.out.println(instance.getClass());

            Object result = callable.call();
            lg.log();
//            Method method1 =  instance.getClass().getDeclaredMethod("getLogger");
//            Object invoke = method1.invoke(instance);
           //  System.out.println(invoke);
//            logger.log();
            //lg.log();
//            Object result = method.invoke(instance, args);
//            System.out.println(instance.getClass().getName());
            // instance.call2();
            System.out.println("bytebuddy method call after   "+method.getName());
            return result;
        }

//        @Advice.OnMethodEnter
//        public static void onMethodEnter(@Advice.Origin Method method,@Advice.AllArguments Object[] arguments){
//            System.out.println(method.getName()+" aop "+arguments[0]);
//        }
    }

    static class Logger extends AbLogger{
        public String name;
        private static Boolean aaa = false;

        public Logger(String name){
            this.name = name;
        }

        public void log(){
            System.out.println(aaa);
            System.out.println("log:"+name);
        }
    }

    public static class AbLogger{
        public void abLog(){
            System.out.println("abLog");
        }
    }


}
