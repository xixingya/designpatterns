package tech.xixing.compile.union;

/**
 * @author liuzhifei
 * @since 1.0
 */
/**
 * @description: 算子定义接口
 *
 */
public interface ZcOperator<T> {

    /**
     * 算子运算操作
     * @return ZcResult 返回结果
     */
    ZcResult<T> operator();

}
