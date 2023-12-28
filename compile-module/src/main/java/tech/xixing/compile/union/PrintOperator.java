package tech.xixing.compile.union;

/**
 * @author liuzhifei
 * @since 1.0
 */
/**
 * @description: 打印函数
 */
public class PrintOperator implements ZcOperator{
    /**
     * 打印方式标记
     */
    private int flag;
    /**
     * 打印目标
     */
    private Object target;

    public PrintOperator(int flag, Object target) {
        this.flag = flag;
        this.target = target;
    }

    public ZcResult operator() {
        Object t = target ;
        if(target instanceof ZcOperator){
            ZcOperator zo = (ZcOperator) target;
            t =zo.operator().getData();
        }

        switch (flag){
            case 1:
                System.out.print(t);
            case 2:
                System.out.println(t);
        }
        return ZcVoid.getInstance();
    }
}
