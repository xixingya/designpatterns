package tech.xixing.design.pattern.creational.builder;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 18:37
 */
public abstract class CourseBuilder {

    /*private String courseName;
    private String coursePPT;
    private String courseArticle;
    private String courseQA;*/

    public abstract void buildCourseName(String courseName);
    public abstract void buildCoursePPT(String courePPT);
    public abstract void buildCOurseVideo(String courseVideo);
    public abstract void buildCourseArticle(String courseArticle);
    public abstract void buildcourseQA(String courseQA);

    public abstract Course makeCourse();
}
