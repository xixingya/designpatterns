package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import tech.xixing.datasync.config.SQLConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author liuzhifei
 * @date 2023/1/11 10:48 上午
 */
@Slf4j
public class SQLTransformer {

    private SQLConfig sqlConfig;

    public SQLTransformer(SQLConfig sqlConfig){
        this.sqlConfig = sqlConfig;
    }

    public List<JSONObject> transform(String jsonArray) {
        sqlConfig.setData(jsonArray);
        List<JSONObject> res = new ArrayList<>();
        try{
            ResultSet resultSet = sqlConfig.getStatement().executeQuery();
            LinkedHashMap<String, Class<?>> fields = sqlConfig.getFields();
            while (resultSet.next()) {
                JSONObject jo = new JSONObject();
                int n = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= n; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Class<?> aClass = fields.get(columnName);
                    Object object = resultSet.getObject(i);
                    if(JSONObject.class.equals(aClass)){
                        object = JSONObject.parseObject(object.toString());
                    }
                    if(JSONArray.class.equals(aClass)){
                        object = JSONObject.parseArray(object.toString());
                    }
                    jo.put(resultSet.getMetaData().getColumnLabel(i), object);
                }
                res.add(jo);
                // System.out.println(jo.toJSONString());
            }
        }catch (Exception e){
            log.error("exec query error sql = {}",sqlConfig.getSql(),e);
        }
        return res;
    }

    public void registerUdf(String name, Class<?> clazz) throws SQLException {
        SchemaPlus rootSchema = sqlConfig.getRootSchema();
        // sqlConfig.rePrepared();
        rootSchema.add(name, ScalarFunctionImpl.create(clazz,"execute"));
    }

    public static void main(String[] args) {

    }

}
