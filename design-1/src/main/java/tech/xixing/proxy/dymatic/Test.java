package tech.xixing.proxy.dymatic;

import tech.xixing.proxy.statics.MDao;
import tech.xixing.proxy.statics.MemberDao;

import java.lang.reflect.Proxy;

/**
 * @author liuzhifei
 * @date 2022/2/7 7:52 下午
 */
public class Test {

    public static void main(String[] args) {
        MDao mDao =new MemberDao();
        /*
         * 1.会产生一段字符串，代理类的源码
         * 2.把字符串输出到一个.java文件($Proxy.java)当中
         * jvm从3开始
         * 3.把这个java文件动态编译成$Proxy.class
         * 4.会通过一个类加载器把这个class加载带jvm中
         * 5.Class.forName("xxx").newInstance 反射实例化这个对象 proxyObject
         */
        MDao dao = (MDao) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{MDao.class}, new CustomInvocationHandler(mDao));
        dao.query(1,"qaq");
    }
}
