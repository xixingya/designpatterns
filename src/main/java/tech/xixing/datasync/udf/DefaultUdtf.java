package tech.xixing.datasync.udf;

import com.google.common.collect.ImmutableList;
import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.FunctionParameter;
import org.apache.calcite.schema.QueryableTable;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.TableFunction;
import org.apache.calcite.sql.type.SqlTypeName;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuzhifei
 * @date 2023/1/16 4:15 下午
 */
public class DefaultUdtf{

    public static QueryableTable split(String str,String reg){
        List<String> items;
        if(str==null){
            items = ImmutableList.of();
        }else {
            items = Arrays.stream(str.split(reg)).collect(Collectors.toList());
        }
        final Enumerable<String> enumerable = Linq4j.asEnumerable(items);
        return new AbstractQueryableTable(Integer.class) {
            @Override public <E> Queryable<E> asQueryable(
                    QueryProvider queryProvider, SchemaPlus schema, String tableName) {
                //noinspection unchecked
                return (Queryable<E>) enumerable.asQueryable();
            }

            @Override public RelDataType getRowType(RelDataTypeFactory typeFactory) {
                return typeFactory.builder().add("split", SqlTypeName.VARCHAR).build();
            }
        };
    }
}
