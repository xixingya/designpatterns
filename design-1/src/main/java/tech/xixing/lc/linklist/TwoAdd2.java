package tech.xixing.lc.linklist;

import tech.xixing.lc.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class TwoAdd2 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode preRes = new ListNode(0);
        ListNode cur = preRes;
        // 进位
        int nextLevel = 0;
        while (l1 != null || l2 != null) {
            int add1 = l1 == null ? 0 : l1.val;
            int add2 = l2 == null ? 0 : l2.val;
            int sum = add1 + add2 + nextLevel;
            nextLevel = sum / 10;
            sum = sum % 10;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            cur.next = new ListNode(sum);
            cur = cur.next;
        }
        if (nextLevel == 1) {
            cur.next = new ListNode(nextLevel);
        }
        return preRes.next;

    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int totalLen = nums1.length + nums2.length;
        boolean find2 = totalLen % 2 == 0;
        int half = totalLen / 2;
        int i = 0;
        int j = 0;
        int[] merged = new int[totalLen];
        int mergedIndex = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                merged[mergedIndex] = nums2[j];
                j++;
            } else {
                merged[mergedIndex] = nums1[i];
                i++;
            }
            mergedIndex++;
        }
        if (i < nums1.length) {
            for (int k = i; k < nums1.length; k++) {
                merged[mergedIndex] = nums1[k];
                mergedIndex++;
            }
        }
        if (j < nums2.length) {
            for (int k = j; k < nums2.length; k++) {
                merged[mergedIndex] = nums2[k];
                mergedIndex++;
            }
        }
        if (find2) {
            return (double) (merged[half - 1] + merged[half]) / 2;
        } else {
            return merged[half];
        }
    }

    public static String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        int maxLen = 1;

        int l = 0;
        int r = 0;
        for (int right = 1; right < len; right++) {
            for (int left = 0; left < right; left++) {
                boolean b = s.charAt(left) == s.charAt(right);
                if (b && right - left <= 1) {
                    dp[left][right] = true;
                } else if (b) {
                    dp[left][right] = dp[left + 1][right - 1];
                }
                if (dp[left][right]) {
                    if(right-left+1>maxLen){
                        maxLen = right-left+1;
                        l = left;
                        r = right;
                    }
                }
            }
        }
        return s.substring(l, r + 1);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("abaccacca"));
    }
}
