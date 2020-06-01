package tech.xixing.design.pattern.creational.builder.v2;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 19:34
 */
public class Test {
    public static void main(String[] args) {
        Course course=new Course.CourseBuilder().buildCourseArticle("Java设计模式").buildCourseVideo("Java设计模式视频").build();
        System.out.println(course);

    }



}
