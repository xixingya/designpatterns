package tech.xixing.design.pattern.creational.simplefactory;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 11:02
 */
public class Test {
    public static void main(String[] args) {

        /*VideoFactory videoFactory=new VideoFactory();
        AbstractVideo video = videoFactory.getVideo("java");

        if(video==null){
            return;
        }
        video.produce();*/

        VideoFactory videoFactory=new VideoFactory();
        AbstractVideo video = null;
        try {
            video = videoFactory.getVideo(JavaVideo.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        video.produce();

        if(video==null){
            return;
        }

    }
}
