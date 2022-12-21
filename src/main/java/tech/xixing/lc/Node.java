package tech.xixing.lc;

import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhifei
 * @date 2022/9/5 4:45 下午
 */
public class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node(){

    }

    public Node(int val){
        this.val  = val;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new TextMappingJackson2HttpMessageConverter());
        ResponseEntity<Map> response = restTemplate.getForEntity("https://www.baidu.com/sugrec?pre=1&p=3&ie=utf-8&json=1&prod=pc&from=pc_web&wd=我是", Map.class);
        System.out.println(response);
    }

    static class TextMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter{
        public TextMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
