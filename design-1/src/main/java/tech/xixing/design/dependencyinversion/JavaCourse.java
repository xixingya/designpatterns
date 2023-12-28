package tech.xixing.design.dependencyinversion;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 8:41
 */
public class JavaCourse implements ICourse {
    @Override
    public void study() {
        System.out.println("study Java");
    }
}
