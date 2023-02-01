package tech.xixing.datasync;

import org.apache.calcite.sql.parser.SqlParseException;
import tech.xixing.datasync.anno.EnableCustomUdf;
import tech.xixing.datasync.config.SQLConfig;

import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * @author liuzhifei
 */
@EnableCustomUdf(scanPackage = "tech.xixing.datasync.udf")
public class UdfDemo {
    public static void main(String[] args) throws SqlParseException, SQLException {

        LinkedHashMap<String,Object> fields = new LinkedHashMap<>();
        fields.put("uid",String.class);
        fields.put("roomTag",String.class);
        fields.put("roomStatus",String.class);
        SQLConfig sqlConfig = new SQLConfig("select * from kafka.test","test",fields);
    }
}
