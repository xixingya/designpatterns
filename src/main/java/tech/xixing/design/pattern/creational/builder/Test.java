package tech.xixing.design.pattern.creational.builder;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 19:15
 */
public class Test {
    public static void main(String[] args) {
        CourseBuilder courseBuilder=new CourseActualBuilder();
        Coach coach=new Coach();
        coach.setCourseBuilder(courseBuilder);
        Course course = coach.makeCourse("Java设计模式", "Java设计模式ppt",
                "Java设计模式手记", "Java设计模式问答", "Java设计模式视频");
        System.out.println(course.toString());

    }
}
