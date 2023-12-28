package tech.xixing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class K8sUtil {

    private static final Pattern pattern = Pattern.compile("([a-z]+[0-9a-z]+-[a-z]+[0-9a-z-]+)(?<!-)");

    public static String getK8sPod(String content){
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "";
    }
}
