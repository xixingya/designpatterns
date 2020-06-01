package tech.xixing.design.pattern.creational.abstractfactory;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 16:49
 */
public class Test {

    public static void main(String[] args) {
        CourseFactory javaCourseFactory=new JavaCourseFactory();
        Video javaCourseFactoryVideo = javaCourseFactory.getVideo();
        Article javaCourseFactoryArticle = javaCourseFactory.getArticle();
        javaCourseFactoryVideo.produce();
        javaCourseFactoryArticle.produce();

        CourseFactory pythonFactory = new PythonFactory();
        Article pythonFactoryArticle = pythonFactory.getArticle();
        Video pythonFactoryVideo = pythonFactory.getVideo();
        pythonFactoryVideo.produce();
        pythonFactoryArticle.produce();


    }
}
