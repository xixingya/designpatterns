package tech.xixing.design.openclose;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/30 17:09
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        ICourse iCourse=new JavaDiscountCourse(98,"Java从入门到入坟",999d);

        JavaDiscountCourse javaDiscountCourse=(JavaDiscountCourse) iCourse;

        log.info("course.id={},name={},price={},originPrice={}",javaDiscountCourse.getId(),javaDiscountCourse.getName(),javaDiscountCourse.getPrice(),javaDiscountCourse.getOriginPrice());

    }
}
