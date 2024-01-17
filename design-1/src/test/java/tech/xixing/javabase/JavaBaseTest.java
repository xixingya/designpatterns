package tech.xixing.javabase;

import org.junit.Test;

import java.util.Objects;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class JavaBaseTest {

    @Test
    public void testEquals() {
        A a = new A("1", "2");
        A b = new A("1", "2");
        assert a.equals(b);
    }


    @Test
    public void testFinally() {
        assert testFinally1() == 2;
        assert testFinally2() == 3;
    }

    @Test
    public void testHashMap() {

    }

    public static int testFinally2() {
        try {
            return 2;
        } finally {
            return 3;
        }
    }

    public static int testFinally1() {
        int i = 0;
        try {
            i = 2;
            return i;
        } finally {
            // 虽然是return之前执行，但是这个赋值没有用
            i = 3;
        }
    }


    static class A {

        public A(String var1, String var2) {
            this.var1 = var1;
            this.var2 = var2;
        }

        String var1;
        String var2;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof A)) return false;
            A a = (A) o;
            return Objects.equals(var1, a.var1) && Objects.equals(var2, a.var2);
        }

//        @Override
//        public int hashCode() {
//            return Objects.hash(var1, var2);
//        }
    }

}
