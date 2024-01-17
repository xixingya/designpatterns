package tech.xixing.design.pattern.creational.abstractfactory;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 16:49
 */
public class PythonFactory implements CourseFactory {
    @Override
    public Video getVideo() {
        return new PythonVideo();
    }

    @Override
    public Article getArticle() {
        return new PythonArticle();
    }
}
