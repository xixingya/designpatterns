package tech.xixing.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuzhifei
 * @date 2022/4/12 3:45 下午
 */
public class ClassTest {

    public static void main(String[] args) throws ClassNotFoundException {
       // Class<?> aClass = Class.forName("tech.xixing.common.MyStaticCode");

        String regEx ="\\d+";






        String s = "SocialMatchService fill uid:123456";

        boolean matches = s.matches(regEx);
        System.out.println(matches);
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            System.out.println(matcher.group());
        }

    }
}
