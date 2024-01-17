package tech.xixing.datasync.config;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author liuzhifei
 * @date 2023/1/13 4:40 下午
 */
@Data
public class UdfConfig {
    private String name;
    private Method method;
}
