package tech.xixing.design.pattern.creational.builder;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 18:44
 */
public class CourseActualBuilder extends CourseBuilder {

    Course course=new Course();

    @Override
    public void buildCourseName(String courseName) {
        course.setCourseName(courseName);
    }

    @Override
    public void buildCoursePPT(String courePPT) {
        course.setCoursePPT(courePPT);
    }

    @Override
    public void buildCOurseVideo(String courseVideo) {
        course.setCourseVideo(courseVideo);
    }

    @Override
    public void buildCourseArticle(String courseArticle) {
        course.setCourseArticle(courseArticle);
    }

    @Override
    public void buildcourseQA(String courseQA) {
        course.setCourseQA(courseQA);
    }

    @Override
    public Course makeCourse() {
        return course;
    }
}
