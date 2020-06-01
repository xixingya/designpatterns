package tech.xixing.design.pattern.creational.abstractfactory;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 16:45
 */
public class JavaArticle extends Article {
    @Override
    public void produce() {
        System.out.println("编写Java手记");
    }
}
