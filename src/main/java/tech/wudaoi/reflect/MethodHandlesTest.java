package tech.wudaoi.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author sheyang
 * @date 2024/1/17 09:51
 */
public class MethodHandlesTest {

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findStatic(StaticDemo.class, "buildGroup",
                MethodType.methodType(String.class));
        String result = (String) methodHandle.invoke();
        System.out.println(result);
    }

}
