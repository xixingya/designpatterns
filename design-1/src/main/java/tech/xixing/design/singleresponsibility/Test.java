package tech.xixing.design.singleresponsibility;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 9:04
 */
public class Test {

    public static void main(String[] args) {
        /*Bird bird=new Bird();
        bird.mainMoveMode("鸵鸟");
        bird.mainMoveMode("大雁");*/

        WalkBird walkBird=new WalkBird();
        walkBird.mainMoveMode("鸵鸟");

        FlyBird flyBird=new FlyBird();
        flyBird.mainMoveMode("大雁");
    }
}
