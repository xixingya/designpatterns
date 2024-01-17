package tech.xixing.datasync.udf;

import tech.xixing.datasync.anno.Udf;

import java.util.List;

/**
 * @author liuzhifei
 * @date 2022/12/21 5:41 下午
 */
public class TestUdf {

    // @Udf(name = "test_split")
    public static String[] split(String str,String reg){
        if(str==null){
            return null;
        }
        return str.split(reg);
    }
}
