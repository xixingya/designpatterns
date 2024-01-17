package tech.xixing.compile.union;

/**
 * @author liuzhifei
 * @since 1.0
 */

import java.util.Set;

/**
 * @description: 集合定义算子
 */
public class SetOperator implements ZcOperator<Set>{

    /**
     * 结果集
     */
    private ZcResult<Set> result;

    public SetOperator(Set data){
        result = new ZcResult<Set>(data);
    }

    public ZcResult<Set> operator() {
        return result;
    }
}
