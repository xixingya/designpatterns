package tech.xixing.code.odps;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhifei
 * @date 2022/9/21 2:15 下午
 */
public class DataTaskFilter {

    public static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {



        String url = "https://xxx.com/rest/code/search?projectId=42123&tenantId=29364&keyword={keyword}&useTypes=0&fileTypes=&lastEditTimeFrom=&lastEditTimeTo=&pageNum={pageNum}&pageSize=5000";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("cookie","12345");

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("pageNum","1");
        paramMap.put("keyword","123456");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(null,httpHeaders);
        ResponseEntity<Map> response2 = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class,paramMap);

        Map<String, Object> data = getData(response2);
        Boolean hasMore = (Boolean)data.get("hasMore");
        if(hasMore){
            throw new RuntimeException("超过5000个");
        }
        List<Map<String,Object>> searchResult =(List) data.get("searchResult");

        String thrFileFindTaskUrl = "https://xxx.com/rest/file?projectId=42123&tenantId=29364&fileId={fileId}&openType=0";
        String taskDetailUrl = "https://xxx.com/workbench/cwf/instance/list?projectId=42123&env=prod&tenantId=29364&sortOrder=&sortField=&includeRelation=false&dagType=0&taskStatuses=&pageNum=1&pageSize=40&forceUpdateId=1&expand=false&prgTypes=&searchText={searchText}&owner=&solId=&bizId=&nodeTag=&startRunTimeFrom=&startRunTimeTo=&baseLineId=&connectionRegionId=&connectionType=&connections=&slowly=false&diResGroupIdentifier=&diSrcType=&diDstType=&diSrcDatasource=&diDstDatasource=&resgroupId=&bizdate=2022-09-19%2000%3A00%3A00&beginBizDate=&endBizDate=&bizBeginHour=2022-09-20%2000%3A00%3A00&bizEndHour=2022-09-20%2023%3A59%3A00&defaultDateType=yesterday";
        for (Map<String, Object> stringObjectMap : searchResult) {
            Integer fileId = (Integer)stringObjectMap.get("fileId");
            Map<String,String> params = new HashMap<>();
            params.put("fileId",fileId.toString());
            // 调用接口获取文件对应的调度id
            ResponseEntity<Map> responseFile = restTemplate.exchange(thrFileFindTaskUrl, HttpMethod.GET, httpEntity, Map.class,params);
            Map<String, Object> dataFile = getData(responseFile);
            Map<String,Object> file = (Map<String, Object>) dataFile.get("file");
            Long cloudUuid =(Long) file.get("cloudUuid");
            Map<String,String> params2 = new HashMap<>();
            if(cloudUuid==null){
                stringObjectMap.put("statusName","无调度");
                System.out.println("找不到taskId searchResult 需要确认是否有调度 文件名为 = " +stringObjectMap.get("fileName"));
                continue;
            }else {
                params2.put("searchText",cloudUuid.toString());
            }


            // 获取详情接口
            ResponseEntity<Map> taskDetail = restTemplate.exchange(taskDetailUrl, HttpMethod.GET, httpEntity, Map.class, params2);
            Map<String, Object> dataDesc = getData(taskDetail);
            List<Map<String,Object>> dataInner = (List)dataDesc.get("data");
            if(dataInner.size()<1){
                stringObjectMap.put("statusName","无节点数据");
                System.out.println("taskId="+cloudUuid+" 查询不到数据");
                continue;
                // throw new RuntimeException("查询不到详情数据");
            }
            // 把数据塞到最初的map中。
            Map<String, Object> objectMap = dataInner.get(0);
            stringObjectMap.putAll(objectMap);
        }
        for (int i = 0; i < searchResult.size(); i++) {
            StringBuilder sb = new StringBuilder();
            Map<String, Object> stringObjectMap = searchResult.get(i);
            String fileName = (String)stringObjectMap.getOrDefault("fileName", "无");
            sb.append(fileName).append("\t");
            String nodeId = stringObjectMap.getOrDefault("nodeId", -1).toString();
            sb.append(nodeId).append("\t");
            String nodeName = (String)stringObjectMap.getOrDefault("nodeName", "无");
            sb.append(nodeName).append("\t");
            String statusName = (String)stringObjectMap.getOrDefault("statusName", "无");
            sb.append(statusName).append("\t");
            String statusDesc = (String)stringObjectMap.getOrDefault("statusDesc", "无");
            sb.append(statusDesc).append("\t");

            Object beginRunningTime = stringObjectMap.getOrDefault("beginRunningTime", -1);
            sb.append(timestamp2String(beginRunningTime.toString())).append("\t");
            sb.append("蒋宏志");
            System.out.println(sb.toString());
        }

    }

    private static Map<String,Object> getData(ResponseEntity<Map> response) {
        Map responseBody = response.getBody();
        Map<String,Object> data = (Map<String,Object>) responseBody.get("data");
        return data;
    }

    public static String timestamp2String(String lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(new Long(lt));
        res = simpleDateFormat.format(date);
        return res;
    }
}
