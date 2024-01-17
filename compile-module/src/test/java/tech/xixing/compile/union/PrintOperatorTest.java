package tech.xixing.compile.union;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liuzhifei
 * @since 1.0
 */
class PrintOperatorTest {
    @Test
    void test1() throws ParseException {
        String prog =" a = 1+5*(7-3) ; \n"+
                " println(a); \n"+
                " u1 = {1,4,7+9}; \n"+
                " u2 = {'red','yellow'}; \n"+
                " u3 ={10+6,'red','ind'}; \n"+
                " unionSet = union(u1,u2,u3); \n"+
                " println(unionSet); \n"+
                " interSet = intersect(union(u1,u2),u3); \n"+
                " println(interSet); \n"+
                " println(subtract(u3,intersect(union(u1,u2),u3)));";

        Zcode zcode = new Zcode(prog);
        zcode.parse();
    }
    
    @Test
    void test2() throws ParseException {
        String exp = "a = '127.0.0.1';\n" +
                "invoker = {'asw1'}; \n" +
                "println(contains(invoker,a));";
        Zcode zcode = new Zcode(exp);
        zcode.parse();
        
    }
    

}