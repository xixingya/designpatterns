package tech.xixing.datasync;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.model.JsonMapSchema;
import org.apache.calcite.schema.SchemaPlus;
import tech.xixing.datasync.adapter.JsonSchema;

import java.sql.*;
import java.util.*;

/**
 * @author liuzhifei
 * @date 2022/6/24 3:13 下午
 */
public class SqlObjectDemo {

    public static void main(String[] args) throws Exception {
        // 1.构建CsvSchema对象，在Calcite中，不同数据源对应不同Schema，比如CsvSchema、DruidSchema、ElasticsearchSchema等
        ReflectiveSchema reflectiveSchema = new ReflectiveSchema(new HrSchema());

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

        // 4.将不同数据源schema挂载到RootSchema，这里添加ReflectiveSchema
        rootSchema.add("hr", reflectiveSchema);

        // 5.执行SQL查询，通过SQL方式访问object对象实例
        String sql = "select * from hr.emps";
        Statement statement = calciteConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // 6.遍历打印查询结果集
        System.out.println(ResultSetUtil.resultString(resultSet));
    }
}
