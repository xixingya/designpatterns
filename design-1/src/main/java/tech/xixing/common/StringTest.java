package tech.xixing.common;

import org.springframework.util.Assert;

import java.util.HashSet;

public class StringTest {
    public static final String qaq = StringTest.class.getName();

    public String getSome(){
        Assert.notNull("qaq",()->"Expected parsed RequestPath in request attribute \"" + qaq + "\".");
        return qaq+"123";
    }

    public static void main(String[] args) {
        //int [][] nums = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        int [][] nums = {{}};
        System.out.println(findNumberIn2DArray(nums, 13));
    }

    public static boolean findNumberIn2DArray(int[][] matrix, int target) {

        for (int i = 0; i < matrix.length; i++) {
            boolean range = range(matrix[i], target);
            if(range){
                int start = 0;
                int end = matrix[i].length-1;
                while (start<=end){
                    int mid = (start+end)/2;
                    if(matrix[i][mid]<target){
                        start=mid+1;
                    }else if(matrix[i][mid]>target){
                        end = mid-1;
                    }else {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean range(int [] nums,int num){
        return nums.length != 0 && nums[0] <= num && nums[nums.length - 1] >= num;
    }
}
