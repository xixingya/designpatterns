package tech.xixing.code.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhifei
 * @date 2022/9/29 2:37 下午
 */
public class RestDemo {
    public static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        // get method for headers and params
        String url = "http://127.0.0.1/demo?pageNum={pageNum}&keyword={keyword}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("cookie","123456");
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("pageNum","1");
        paramMap.put("keyword","123456");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(null,httpHeaders);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class,paramMap);

        // post method for headers and body
        String urlPost = "http://127.0.0.1/demoPost";
        HttpHeaders httpHeadersPost = new HttpHeaders();
        httpHeadersPost.add("cookie","123456");
        MultiValueMap<String,Object> mParamMap = new LinkedMultiValueMap<>();
        mParamMap.add("pageNum","1");
        mParamMap.add("keyword","123456");
        HttpEntity<MultiValueMap<String, Object>> httpEntityPost = new HttpEntity<>(mParamMap,httpHeadersPost);
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(urlPost, httpEntityPost, Map.class);

    }
}
