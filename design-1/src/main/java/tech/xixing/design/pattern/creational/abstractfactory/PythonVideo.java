package tech.xixing.design.pattern.creational.abstractfactory;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 16:47
 */
public class PythonVideo extends Video {
    @Override
    public void produce() {
        System.out.println("录制Python课程");
    }
}
