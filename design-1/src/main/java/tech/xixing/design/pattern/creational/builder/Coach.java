package tech.xixing.design.pattern.creational.builder;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 18:48
 */
public class Coach {
    private CourseBuilder courseBuilder;

    public void setCourseBuilder(CourseBuilder courseBuilder) {
        this.courseBuilder = courseBuilder;
    }

    public Course makeCourse(String courseName,
                             String coursePPT,
                             String courseArticle,
                             String courseQA,
                             String courseVideo) {
        courseBuilder.buildCourseArticle(courseArticle);
        courseBuilder.buildCourseName(courseName);
        courseBuilder.buildCoursePPT(coursePPT);
        courseBuilder.buildcourseQA(courseQA);
        courseBuilder.buildCOurseVideo(courseVideo);

        return courseBuilder.makeCourse();


    }
}
