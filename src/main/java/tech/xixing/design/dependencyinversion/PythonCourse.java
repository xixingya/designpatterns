package tech.xixing.design.dependencyinversion;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 8:45
 */
public class PythonCourse implements ICourse {
    @Override
    public void study() {
        System.out.println("study Python");
    }
}
