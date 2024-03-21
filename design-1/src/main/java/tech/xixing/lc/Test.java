package tech.xixing.lc;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class Test {

    public static void sortedList(int[][] list) {

        
        int[] list1 = merge2list(list[0], list[1]);
        
        for (int i = 2; i < list.length; i++) {
            list1 = merge2list(list1,list[i]);
            //a.add(merge2list(list1,list[i]));
        }
        System.out.println(list1);
        // 
    }

    public static void main(String[] args) {
        sortedList(new int[][]{
                {1,2,4},{2,3,6},{3,4,7}
        });
    }

    private static int[] merge2list(int[] first, int[] second) {
        if (first == null) {
            return second;
        }
        int i = 0;
        int j = 0;
        int k = 0;
        int[] res = new int[first.length + second.length];
        while (i < first.length && j < second.length) {
            if (first[i] < second[j]) {
                res[k] = first[i];
                i++;
            } else {
                res[k] = second[j];
                j++;
            }
            k++;
        }
        while (i < first.length) {
            res[k] = first[i];
            i++;
            k++;
        }
        while (j < second.length) {
            res[k] = second[j];
            j++;
            k++;
        }
        return res;
    }
}
