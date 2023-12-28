package tech.xixing.compile.union;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class StringOperator implements ZcOperator<String>{
    
    private final String data;

    public StringOperator(String data) {
        this.data = data;
    }

    @Override
    public ZcResult<String> operator() {
        return new ZcResult<>(data);
    }
}
