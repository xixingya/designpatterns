package tech.xixing.design.pattern.creational.singleton;

/**
 * @author xixing
 * @version 1.0
 * @date 2020/6/1 14:25
 */
public enum  EnumInstance {
    INSTANCE{

        @Override
        protected void printTest(){
            System.out.println("Test");
        }

    };
    protected abstract void printTest();

    public static EnumInstance getInstance(){
        return INSTANCE;
    }

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
