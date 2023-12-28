package tech.xixing.design.singleresponsibility;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 9:13
 */
public interface ICourse {

    String getCourseName();
    byte[] getCourseVideo();

    void studyCourse();
    void refundCourse();

}
