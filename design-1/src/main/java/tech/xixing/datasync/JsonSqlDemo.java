package tech.xixing.datasync;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.calcite.model.JsonRoot;

/**
 * @author liuzhifei
 * @date 2022/6/29 7:08 下午
 */
public class JsonSqlDemo {


    public void testRead() throws Exception{
        final ObjectMapper mapper = mapper();
        JsonRoot root = mapper.readValue(
                "{\n"
                        + "  version: '1.0',\n"
                        + "   schemas: [\n"
                        + "     {\n"
                        + "       name: 'FoodMart',\n"
                        + "       types: [\n"
                        + "         {\n"
                        + "           name: 'mytype1',\n"
                        + "           attributes: [\n"
                        + "             {\n"
                        + "               name: 'f1',\n"
                        + "               type: 'BIGINT'\n"
                        + "             }\n"
                        + "           ]\n"
                        + "         }\n"
                        + "       ],\n"
                        + "       tables: [\n"
                        + "         {\n"
                        + "           name: 'time_by_day',\n"
                        + "           factory: 'com.test',\n"
                        + "           columns: [\n"
                        + "             {\n"
                        + "               name: 'time_id'\n"
                        + "             }\n"
                        + "           ]\n"
                        + "         },\n"
                        + "         {\n"
                        + "           name: 'sales_fact_1997',\n"
                        + "           factory: 'com.test',\n"
                        + "           columns: [\n"
                        + "             {\n"
                        + "               name: 'time_id'\n"
                        + "             }\n"
                        + "           ]\n"
                        + "         }\n"
                        + "       ]\n"
                        + "     }\n"
                        + "   ]\n"
                        + "}",
                JsonRoot.class);
    }

    private ObjectMapper mapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        return mapper;
    }
}
