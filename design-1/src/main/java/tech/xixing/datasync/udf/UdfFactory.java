package tech.xixing.datasync.udf;

import cn.hutool.core.util.ClassUtil;
import org.springframework.util.ClassUtils;
import tech.xixing.datasync.anno.EnableCustomUdf;
import tech.xixing.datasync.anno.Udf;
import tech.xixing.datasync.config.UdfConfig;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liuzhifei
 * @date 2023/1/13 3:41 下午
 */
public class UdfFactory {

    private static Set<UdfConfig> globalUdf = new HashSet<>();

    /**
     * key是tableName
     * value是对应table的udf
     */
    private static Map<String,Set<UdfConfig>> tableCustomUdf = new HashMap<>();


    public static Set<UdfConfig> getUdfByTable(String tableName){
        Set<UdfConfig> res = new HashSet<>(globalUdf);
        Set<UdfConfig> udfConfigs = tableCustomUdf.computeIfAbsent(tableName,k->new HashSet<>());
        res.addAll(udfConfigs);
        return res;
    }


    static {
        Class<?> mainApplicationClass = deduceMainApplicationClass();
        if(mainApplicationClass!=null&&mainApplicationClass.isAnnotationPresent(EnableCustomUdf.class)){
            EnableCustomUdf enableCustomUdf = mainApplicationClass.getAnnotation(EnableCustomUdf.class);
            String[] scanPackages = enableCustomUdf.scanPackage();
            for (String scanPackage : scanPackages) {
                setUdfByPackage(scanPackage);
            }
        }
        // 默认的udf仓库
        setUdfByPackage("tech.xixing.datasync.udf");
    }



    /**
     * 获取 main方法所在类
     * @return 返回所在类
     */
    private static Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        }
        catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }

    /**
     * set udf by packageName
     * @param packageName packageName
     */
    private static void setUdfByPackage(String packageName){
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getMethods();
            for (Method declaredMethod : methods) {
                if(declaredMethod.isAnnotationPresent(Udf.class)){
                    Udf udf = declaredMethod.getAnnotation(Udf.class);
                    UdfConfig udfConfig = new UdfConfig();
                    udfConfig.setMethod(declaredMethod);
                    udfConfig.setName(udf.name());
                    String tableName = udf.tableName();
                    if("".equals(tableName)){
                        globalUdf.add(udfConfig);
                    }else {
                        Set<UdfConfig> udfConfigs = tableCustomUdf.computeIfAbsent(tableName, key -> new HashSet<>());
                        udfConfigs.add(udfConfig);
                    }
                }
            }
        }
    }
}
