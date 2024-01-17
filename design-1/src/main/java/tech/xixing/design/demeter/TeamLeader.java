package tech.xixing.design.demeter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 10:25
 */
public class TeamLeader {

    public void checkCourseNum(){
        List<Course> courseList=new ArrayList<>();

        for(int i=0;i<20;i++){
            courseList.add(new Course());
        }
        System.out.println("课程的数量为："+courseList.size());
    }
}
