package tech.xixing.code;

import java.util.*;

/**
 * @author liuzhifei
 * @date 2022/1/25 2:36 下午
 */
public class Code004 {

    public static void main2(String[] args) {
        Map<String, String> map = new HashMap<>();

        map.put("aa", "aa");
        map.put("bb", "bb");
        map.put("cc", "cc");
        map.put("dd", "dd");
        Set<String> strings = map.keySet();
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println(map.values());
    }

    public static void main(String[] args) {
       // 解答失败: 测试用例:"" "a" 测试结果:false 期望结果:true stdout:
        System.out.println(oneEditAway("abc", "adac"));
    }

    public static boolean oneEditAway(String first, String second) {
        int left = 0;
        boolean leftBreak = false;
        boolean rightBreak = false;
        int rightFirst =first.length()-1;
        int rightSceond = second.length()-1;
        if(first.equals(second)){
            return true;
        }
        if(first.length()==0&&second.length()==1||first.length()==1&&second.length()==0){
            return true;
        }
        while (rightFirst>=left&&rightSceond>=left){
            if(first.charAt(left)==second.charAt(left)){
                left++;
            }else {
                leftBreak =true;
            }
            if(first.charAt(rightFirst)==second.charAt(rightSceond)){
                rightSceond--;
                rightFirst--;
            }else {
                rightBreak = true;
            }
            if(leftBreak&&rightBreak){
                break;
            }
        }
        if(rightFirst>rightSceond) {
            return rightFirst==left;
        }else{
            return rightSceond==left;
        }

    }


    public String compressString(String S) {
        if(S.length()==0||S.length()==1){
            return S;
        }
        StringBuilder sb = new StringBuilder();
        char current =S.charAt(0);
        int count = 1;
        for (int i = 1; i < S.length(); i++) {
            if(S.charAt(i)==current){
                count++;
            }else {
                sb.append(current).append(count);
                current = S.charAt(i);
                count = 1;
            }
        }

        return sb.length()>S.length()?S:sb.toString();

    }


}
