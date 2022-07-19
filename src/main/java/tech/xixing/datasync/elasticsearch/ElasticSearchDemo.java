package tech.xixing.datasync.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.calcite.adapter.elasticsearch.ElasticsearchSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import tech.xixing.datasync.ResultSetUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

/**
 * @author liuzhifei
 * @date 2022/7/15 7:27 下午
 */
public class ElasticSearchDemo {

    public static void main(String[] args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 1.构建ElasticsearchSchema对象,在Calcite中,不同数据源对应不同Schema,比如:CsvSchema、DruidSchema、ElasticsearchSchema等
        RestClient restClient = RestClient.builder(new HttpHost("test-es-new.xxx.com", 80)).build();
        // 指定索引库
        Constructor<ElasticsearchSchema> declaredConstructor = ElasticsearchSchema.class.getDeclaredConstructor(RestClient.class, ObjectMapper.class, String.class, int.class);
        declaredConstructor.setAccessible(true);
        // 测试用，把fetch size调到1，可以发现，每次resultset.next()的时候都会调用scroll去es查询，由此可见，使用的是scroll api去查询的，一次查询的size就是这个size
        ElasticsearchSchema elasticsearchSchema = declaredConstructor.newInstance(restClient, new ObjectMapper(), "bigdata_rec_bixin_timeline",5);
        //ElasticsearchSchema elasticsearchSchema = new ElasticsearchSchema(restClient, new ObjectMapper(), "bigdata_rec_bixin_timeline");

        // 2.构建Connection
        // 2.1 设置连接参数
        Properties info = new Properties();
        // 不区分sql大小写
        info.setProperty("caseSensitive", "false");

        // 2.2 获取标准的JDBC Connection
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        // 2.3 获取Calcite封装的Connection
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);

        // 3.构建RootSchema，在Calcite中，RootSchema是所有数据源schema的parent，多个不同数据源schema可以挂在同一个RootSchema下
        // 以实现查询不同数据源的目的
        SchemaPlus rootSchema = calciteConnection.getRootSchema();

        // 4.将不同数据源schema挂载到RootSchema，这里添加ElasticsearchSchema
        rootSchema.add("es", elasticsearchSchema);

        // 5.执行SQL查询，通过SQL方式访问object对象实例
        // 条件查询
        // String sql = "SELECT _MAP['id'],_MAP['title'],_MAP['price'] FROM es.books WHERE _MAP['price'] > 60 LIMIT 2";
        // 统计索引数量
        // String sql = "SELECT count(*) FROM es.books WHERE _MAP['price'] > 50 ";
        // 分页查询
        String sql = "SELECT * FROM es.bigdata_rec_bixin_timeline WHERE _MAP['uid'] > 10 ";
        Statement statement = calciteConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // 6.遍历打印查询结果集
        System.out.println(ResultSetUtil.resultString(resultSet));


    }

}
