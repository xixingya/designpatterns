package tech.xixing.proxy.dymatic.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class Demo2 {
    /**
     * 接口实现
     */
    public static class Call{

        public String call1(String ok) {
            System.out.println(" call 1 ");
            return "";
        }


        public void call2() {
            System.out.println(" call 2 ");
        }
    }


    public static void main(String[] args) throws Exception {
        Call proxy = createByteBuddyDynamicProxy();
        proxy.call1("hihihi");
        //proxy.call2();
    }

    private static Call createByteBuddyDynamicProxy() throws Exception {
        DynamicType.Loaded<Call> proxy = new ByteBuddy().subclass(Call.class)
                .method(ElementMatchers.any())
                .intercept(Advice.to(MethodInterceptor.class))
                .make()
                .load(Demo2.class.getClassLoader());

        // 保存到磁盘 用于测试分析，开发使用的使用不需要使用
        // proxy.saveIn(new File("./so/"));
        // proxy.getLoaded().getDeclaredConstructor(String.class).newInstance("a");
        return (Call)proxy.getLoaded().getDeclaredConstructor().newInstance();

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

        @Advice.OnMethodEnter
        public static void onMethodEnter(@Advice.Origin Method method,@Advice.AllArguments Object[] arguments){
            System.out.println(method.getName()+" aop "+arguments[0]);
        }
    }
}
