package tech.xixing.compile.union;

/**
 * @author liuzhifei
 * @since 1.0
 */
/**
 * @description: 算子运算的结果类
 */
public class ZcResult <T>{
    private final T data;

    public ZcResult(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
