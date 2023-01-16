package tech.xixing.datasync.adapter;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.Linq4j;

import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.linq4j.tree.Expressions;
import org.apache.calcite.linq4j.tree.Types;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Schemas;
import org.apache.calcite.schema.Statistic;
import org.apache.calcite.schema.Statistics;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.util.BuiltInMethod;
import org.apache.calcite.util.Pair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Implementation of {@link org.apache.calcite.schema.Schema} that exposes the
 * public fields and methods in a json.
 */
public class JsonSchema extends AbstractSchema {
    private String target;
    private String databaseName;
    private JSONArray targetArray;

    // 初始化的时候保存的格式，如果没保存，则存在后续不一致问题。
    private LinkedHashMap<String, Class<?>> fields;

    Map<String, Table> table = null;

    /**
     * Creates a JsonSchema.
     *
     * @param target Object whose fields will be sub-objects of the schema
     */
    public JsonSchema(String databaseName, String target) {
        super();
        this.databaseName = databaseName;
        if (!target.startsWith("[")) {
            this.target = '[' + target + ']';
        } else {
            this.target = target;
        }
        final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();

        final Table table = fieldRelation();
        if (table != null) {
            builder.put(databaseName, table);
            this.table = builder.build();
        }
    }

    public JsonSchema(String topic, String target, LinkedHashMap<String,Class<?>> fields) {
        super();
        this.databaseName = topic;
        if (!target.startsWith("[")) {
            this.target = '[' + target + ']';
        } else {
            this.target = target;
        }
        this.fields = fields;
        final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();

        final Table table = fieldRelation();
        if (table != null) {
            builder.put(topic, table);
            this.table = builder.build();
        }
    }

    public void setTarget(String target) {
        this.target = target;
        final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();

        final Table table = fieldRelation();
        if (table != null) {
            builder.put(databaseName, table);
            this.table = builder.build();
        }
    }

    public JsonSchema(String databaseName, JSONArray targetArray) {
        super();
        this.targetArray = targetArray;
        this.databaseName = databaseName;
        final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
        final Table table = fieldRelation();
        builder.put(databaseName, table);
        this.table = builder.build();

    }

    @Override
    public String toString() {
        return "JsonSchema(topic=" + databaseName + ":target=" + target + ")";
    }

    /**
     * Returns the wrapped object.
     *
     * <p>
     * May not appear to be used, but is used in generated code via
     * {@link org.apache.calcite.util.BuiltInMethod#REFLECTIVE_SCHEMA_GET_TARGET}
     * .
     */
    public String getTarget() {
        return target;
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return table;
    }

    /**
     * Returns an expression for the object wrapped by this schema (not the
     * schema itself).
     */
    Expression getTargetExpression(SchemaPlus parentSchema, String name) {
        return Types.castIfNecessary(target.getClass(),
                Expressions.call(Schemas.unwrap(getExpression(parentSchema, name), JsonSchema.class),
                        BuiltInMethod.REFLECTIVE_SCHEMA_GET_TARGET.method));
    }

    private <T> Table fieldRelation() {
        if (targetArray != null) {
            return new JsonTable(targetArray);
        }
        JSONArray jsonarr = JSON.parseArray(target);
        // final Enumerator<Object> enumerator = Linq4j.enumerator(list);
        if(fields!=null){
            return new JsonTable(jsonarr,fields);
        }
        return new JsonTable(jsonarr);
    }

    private static class JsonTable extends AbstractTable implements ScannableTable {
        private final JSONArray jsonarr;

        private final LinkedHashMap<String,Class<?>> fields;
        // private final Enumerable<Object> enumerable;

        public JsonTable(JSONArray obj) {
            this.jsonarr = obj;
            this.fields = null;
        }
        public JsonTable(JSONArray obj,LinkedHashMap<String,Class<?>> fields) {
            this.jsonarr = obj;
            this.fields = fields;
        }

        @Override
        public RelDataType getRowType(RelDataTypeFactory typeFactory) {
            final List<RelDataType> types = new ArrayList<RelDataType>();
            final List<String> names = new ArrayList<String>();
            // 通过传入的字段判断
            if(fields!=null){
                for (String key : fields.keySet()) {
                    names.add(key);
                    Class clazz = fields.get(key);
                    //如果是json类型，则传入string类型
                    if(JSON.class.isAssignableFrom(clazz)){
                        clazz = String.class;
                    }
                    types.add(typeFactory.createJavaType(clazz));
                }
                return typeFactory.createStructType(Pair.zip(names, types));
            }
            //没传field的情况
            JSONObject jsonobj = jsonarr.getJSONObject(0);
            for (String key : jsonobj.keySet()) {
                final RelDataType type;
                Object value = jsonobj.get(key);
                Class clazz = null;
                if(value instanceof JSON){
                    clazz = String.class;
                }else {
                    clazz = value.getClass();
                }
                type = typeFactory.createJavaType(clazz);
                names.add(key);
                types.add(type);
            }
            if (names.isEmpty()) {
                names.add("line");
                types.add(typeFactory.createJavaType(String.class));
            }
            return typeFactory.createStructType(Pair.zip(names, types));
        }

        @Override
        public Statistic getStatistic() {
            return Statistics.UNKNOWN;
        }

        @Override
        public Enumerable<Object[]> scan(DataContext root) {
            return new AbstractEnumerable<Object[]>() {
                @Override
                public Enumerator<Object[]> enumerator() {
                    return new JsonEnumerator(jsonarr,fields);
                }
            };
        }
    }

    public static class JsonEnumerator implements Enumerator<Object[]> {

        private Enumerator<Object[]> enumerator;

        public JsonEnumerator(JSONArray jsonarr) {
            List<Object[]> objs = new ArrayList<Object[]>();
            for (Object obj : jsonarr) {
                objs.add(((JSONObject) obj).values().toArray());
            }
            enumerator = Linq4j.enumerator(objs);
        }

        public JsonEnumerator(JSONArray jsonarr,LinkedHashMap<String,Class<?>> fields) {
            List<Object[]> objs = new ArrayList<Object[]>();

            for (Object obj : jsonarr) {
                JSONObject jsonObject = (JSONObject) obj;
                Object[] objects = new Object[fields.size()];
                int i = 0;
                for (String key : fields.keySet()) {
                    objects[i] = jsonObject.get(key);
                    // objects[i]= jsonObject.computeIfAbsent(key,k->"");
                    i++;
                }
                objs.add(objects);
                //objs.add(((JSONObject) obj).values().toArray());
            }
            enumerator = Linq4j.enumerator(objs);
        }

        @Override
        public Object[] current() {
            return (Object[]) enumerator.current();
        }

        @Override
        public boolean moveNext() {
            return enumerator.moveNext();
        }

        @Override
        public void reset() {
            enumerator.reset();
        }

        @Override
        public void close() {
            enumerator.close();
        }

    }
}