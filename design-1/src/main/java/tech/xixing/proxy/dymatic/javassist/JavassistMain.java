package tech.xixing.proxy.dymatic.javassist;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @author liuzhifei
 * @date 2022/9/19 5:57 下午
 */
public class JavassistMain {

    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault(); // 创建ClassPool
        // 要生成的类名称为com.test.JavassistDemo
        CtClass clazz = cp.makeClass("com.test.JavassistDemo");

        StringBuffer body = null;
        // 创建字段，指定了字段类型、字段名称、字段所属的类
        CtField field = new CtField(cp.get("java.lang.String"),
                "prop", clazz);
        // 指定该字段使用private修饰
        field.setModifiers(Modifier.PRIVATE);

        // 设置prop字段的getter/setter方法
        clazz.addMethod(CtNewMethod.setter("getProp", field));
        clazz.addMethod(CtNewMethod.getter("setProp", field));
        // 设置prop字段的初始化值，并将prop字段添加到clazz中
        clazz.addField(field, CtField.Initializer.constant("MyName"));

        // 创建构造方法，指定了构造方法的参数类型和构造方法所属的类
        CtConstructor ctConstructor = new CtConstructor(
                new CtClass[]{}, clazz);
        // 设置方法体
        body = new StringBuffer();
        body.append("{\n prop=\"MyName\";\n}");
        ctConstructor.setBody(body.toString());
        clazz.addConstructor(ctConstructor); // 将构造方法添加到clazz中

        // 创建execute()方法，指定了方法返回值、方法名称、方法参数列表以及
        // 方法所属的类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute",
                new CtClass[]{}, clazz);
        // 指定该方法使用public修饰
        ctMethod.setModifiers(Modifier.PUBLIC);
        // 设置方法体
        body = new StringBuffer();
        body.append("{\n System.out.println(\"execute():\" " +
                "+ this.prop);");
        body.append("\n}");
        ctMethod.setBody(body.toString());
        clazz.addMethod(ctMethod); // 将execute()方法添加到clazz中
        // 将上面定义的JavassistDemo类保存到指定的目录
        clazz.writeFile("/data/qaq/");
        // 加载clazz类，并创建对象
        Class<?> c = clazz.toClass();
        Object o = c.newInstance();
        // 调用execute()方法
        Method method = o.getClass().getMethod("execute",
                new Class[]{});
        method.invoke(o, new Object[]{});
    }
}
