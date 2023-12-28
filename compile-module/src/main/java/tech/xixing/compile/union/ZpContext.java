package tech.xixing.compile.union;

/**
 * @author liuzhifei
 * @since 1.0
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 脚本解析上下文
 */
public class ZpContext {

    protected ZpContext(){}

    private Map<String, ZcResult> context = new ConcurrentHashMap<String, ZcResult>(256);


    /**
     *  是否存在变量名
     * @param var 变量名
     * @return
     */
    public boolean existVariable(String var){
        return context.containsKey(var);
    }

    /**
     * 放置变量
     * @param var 变量名
     * @param data 变量值
     */
    public void putVariable(String var,Object data){
        context.put(var,new ZcResult(data));
    }

    /**
     * 获取变量值
     * @param var 变量名
     * @return <T> 返回的结果
     */
    public <T> T getVariable(String var){
        ZcResult result = context.get(var);
        //不存在的变量会抛错
        if (null == result){
            throw new IllegalArgumentException("不存在变量:"+var);
        }
        return (T) result.getData();
    }

}
