package tech.xixing.design.pattern.creational.factorymethod;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 15:01
 */
public class JavaVideoFactory extends VideoFactory {
    @Override
    public AbstractVideo getVideo() {
        return new JavaVideo();
    }
}
