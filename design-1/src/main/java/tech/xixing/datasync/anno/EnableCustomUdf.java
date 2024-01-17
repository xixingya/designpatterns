package tech.xixing.datasync.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuzhifei
 * @date 2023/1/13 4:00 下午
 * @see tech.xixing.datasync.udf.UdfFactory
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableCustomUdf {
    String[] scanPackage();
}
