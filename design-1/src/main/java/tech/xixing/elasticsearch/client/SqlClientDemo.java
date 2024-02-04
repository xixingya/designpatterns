package tech.xixing.elasticsearch.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;

import java.io.IOException;
import java.sql.*;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class SqlClientDemo {
    public static void main(String[] args) throws Exception {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")))) {

            // 检查集群信息
            MainResponse response = client.info(RequestOptions.DEFAULT);
            System.out.println(response.getVersion().getNumber());

//            // 定义SQL查询请求
//            SQLQueryRequest sqlQueryRequest = new SQLQueryRequest("SELECT * FROM your_index LIMIT 10");
//
//            // 执行SQL查询
//            SQLResponse sqlResponse = client.sql().query(sqlQueryRequest, RequestOptions.DEFAULT);
//
//            // SQLResponse 包含了结果集
//            for (SQLQueryResponse.Row row : sqlResponse.getRows()) {
//                // 处理每一行的结果
//                System.out.println(row.getValues());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
