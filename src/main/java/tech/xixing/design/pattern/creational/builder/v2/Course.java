package tech.xixing.design.pattern.creational.builder.v2;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 19:23
 */
public class Course {

    private String courseName;
    private String coursePPT;
    private String courseArticle;
    private String courseQA;
    private String courseVideo;

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", coursePPT='" + coursePPT + '\'' +
                ", courseArticle='" + courseArticle + '\'' +
                ", courseQA='" + courseQA + '\'' +
                ", courseVideo='" + courseVideo + '\'' +
                '}';
    }

    public Course(CourseBuilder courseBuilder){

        this.courseArticle=courseBuilder.courseArticle;
        this.courseName=courseBuilder.courseName;
        this.coursePPT=courseBuilder.coursePPT;
        this.courseQA=courseBuilder.courseQA;
        this.courseVideo=courseBuilder.courseVideo;

    }

    public static class CourseBuilder {
        private String courseName;
        private String coursePPT;
        private String courseArticle;
        private String courseQA;
        private String courseVideo;

        public CourseBuilder buildCourseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public CourseBuilder buildCoursePPT(String coursePPT) {
            this.coursePPT = coursePPT;
            return this;
        }

        public CourseBuilder buildCourseArticle(String courseArticle) {
            this.courseArticle = courseArticle;
            return this;
        }

        public CourseBuilder buildCourseQA(String courseQA) {
            this.courseQA = courseQA;
            return this;
        }

        public CourseBuilder buildCourseVideo(String courseVideo) {
            this.courseVideo = courseVideo;
            return this;
        }

        public Course build(){
            return new Course(this);
        }


    }
}
