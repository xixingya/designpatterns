package tech.xixing.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @author sheyang
 * @date 2023/4/6 10:12
 */
@Slf4j
public class UrlCodecUtil {

    public static String decode(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        try {
            return URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            log.error("url decode error", e);
        }
        return "";
    }

    public static String decodeForSpringFramework(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        try {
            return UriUtils.encode(source, Charset.defaultCharset());
        } catch (Exception e) {
            log.error("url decode error", e);
        }
        return "";
    }

    public static String encode(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }

        try {
            return URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            log.error("url encode error", e);
        }
        return "";
    }

}
