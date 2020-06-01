package tech.xixing.design.pattern.creational.singleton;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 8:46
 */
public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //LazySingleton lazySingleton=LazySingleton.getInstance();

        Thread t1=new Thread(new T());
        Thread t2=new Thread(new T());
        t1.start();
        t2.start();
        System.out.println("end");


        /*HungrySingleton hungrySingleton=HungrySingleton.getInstance();
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("object_ins"));
        oos.writeObject(hungrySingleton);

        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("object_ins"));
        HungrySingleton hungrySingleton1 = (HungrySingleton) ois.readObject();


        System.out.println(hungrySingleton);
        System.out.println(hungrySingleton1);
        System.out.println(hungrySingleton1 instanceof HungrySingleton);
        System.out.println(hungrySingleton==hungrySingleton1);*/

        /*Class objectClass=LazySingleton.class;
        LazySingleton lazySingleton = LazySingleton.getInstance();
        Field flag=lazySingleton.getClass().getDeclaredField("flag");
        flag.setAccessible(true);
        flag.set(lazySingleton,true);
        Constructor constructor=objectClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        LazySingleton lazySingleton1 = (LazySingleton) constructor.newInstance();
        //HungrySingleton hungrySingleton =(HungrySingleton) constructor.newInstance();
        //HungrySingleton hungrySingleton1=HungrySingleton.getInstance();
        System.out.println(lazySingleton);
        System.out.println(lazySingleton1);*/


        /*EnumInstance enumInstance=EnumInstance.getInstance();
        enumInstance.setData(new Object());
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("object_ins"));
        oos.writeObject(enumInstance);

        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("object_ins"));
        EnumInstance enumInstance1= (EnumInstance) ois.readObject();


        System.out.println(enumInstance.getData());
        System.out.println(enumInstance1.getData());
        System.out.println(enumInstance instanceof EnumInstance);
        System.out.println(enumInstance.getData()==enumInstance1.getData());*/

        //EnumInstance enumInstance = EnumInstance.getInstance();
        /*Class objectClass=EnumInstance.class;
        Constructor constructor=objectClass.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        constructor.newInstance("dfdf", 666)*/
        //enumInstance.printTest();


    }
}
