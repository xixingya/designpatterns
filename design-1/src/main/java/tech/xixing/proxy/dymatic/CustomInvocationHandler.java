package tech.xixing.proxy.dymatic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author liuzhifei
 * @date 2022/2/7 7:54 下午
 */
@Slf4j
public class CustomInvocationHandler implements InvocationHandler {

    Object object;

    public CustomInvocationHandler(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("xxxxxxxxx---jdkProxy before");
        Object invoke = method.invoke(object, args);
        log.debug("xxxxxxxxx---jdkProxy after");
        return invoke;
    }
}
