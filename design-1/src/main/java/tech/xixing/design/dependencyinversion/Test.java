package tech.xixing.design.dependencyinversion;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 8:38
 */
public class Test {

    //v1
    /*public static void main(String[] args) {
        Geely geely=new Geely();
        geely.studyJava();
        geely.studyPython();
    }*/

    //v2
    /*public static void main(String[] args) {
        Geely geely=new Geely();
        geely.studyImoocCourse(new JavaCourse());
        geely.studyImoocCourse(new FECourse());
        geely.studyImoocCourse(new PythonCourse());
    }*/

    //v3
    /*public static void main(String[] args) {
        Geely geely=new Geely(new JavaCourse());
        geely.studyImoocCourse();
    }*/


    public static void main(String[] args) {
        Geely geely=new Geely();
        geely.setiCourse(new JavaCourse());
        geely.studyImoocCourse();
        geely.setiCourse(new PythonCourse());
        geely.studyImoocCourse();
        geely.setiCourse(new FECourse());
        geely.studyImoocCourse();
    }
}
