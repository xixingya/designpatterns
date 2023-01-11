package tech.xixing.datasync.exec;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            while (resultSet.next()) {
                JSONObject jo = new JSONObject();
                int n = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= n; i++) {
                    jo.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                }
                res.add(jo);
                // System.out.println(jo.toJSONString());
            }
        }catch (Exception e){
            log.error("exec query error sql = {}",sqlConfig.getSql(),e);
        }
        return res;
    }

    public static void main(String[] args) {

    }

}
