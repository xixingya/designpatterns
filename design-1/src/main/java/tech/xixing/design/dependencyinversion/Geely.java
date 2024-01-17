package tech.xixing.design.dependencyinversion;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 8:37
 */
public class Geely {
    private ICourse iCourse;

    public void setiCourse(ICourse iCourse) {
        this.iCourse = iCourse;
    }



    /*public void sutdyJava(){
        System.out.println("study Java");
    }

    public void studyPE(){
        System.out.println("study PE");
    }

    public void studyPython(){
        System.out.println("study Python");
    }*/

    public void studyImoocCourse(){
        iCourse.study();
    }
}
