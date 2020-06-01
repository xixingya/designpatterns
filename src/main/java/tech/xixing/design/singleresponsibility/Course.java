package tech.xixing.design.singleresponsibility;

import javax.swing.*;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 9:19
 */
public class Course implements ICourseContent,ICourseManagement {
    @Override
    public String getCourseName() {
        return null;
    }

    @Override
    public byte[] getCourseVideo() {
        return new byte[0];
    }

    @Override
    public void studyCourse() {

    }

    @Override
    public void refundCourse() {

    }
}
