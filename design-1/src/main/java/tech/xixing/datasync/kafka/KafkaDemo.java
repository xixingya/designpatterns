package tech.xixing.datasync.kafka;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author liuzhifei
 * @date 2022/7/15 7:20 下午
 */
public class KafkaDemo {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:calcite:");
        CalciteConnection optiqConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = optiqConnection.getRootSchema();

    }
}
