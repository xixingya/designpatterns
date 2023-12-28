package tech.xixing.design.pattern.creational.simplefactory;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/5/31 11:04
 */
public class VideoFactory {
    /*public AbstractVideo getVideo(String type){
        if("java".equalsIgnoreCase(type)){
            return new JavaVideo();
        }else if("python".equalsIgnoreCase(type)){
            return new PythonVideo();
        }
        return null;
    }*/

    public AbstractVideo getVideo(Class type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        AbstractVideo video=null;
        video= (AbstractVideo) Class.forName(type.getName()).newInstance();
        return video;


    }
}
