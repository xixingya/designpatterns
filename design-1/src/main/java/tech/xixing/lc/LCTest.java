package tech.xixing.lc;

import java.util.*;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class LCTest {

    public int maxDistance(List<List<Integer>> arrays) {
        Integer minVal = arrays.get(0).get(0);
        Integer size0 = arrays.get(0).size();
        Integer maxVal = arrays.get(0).get(size0 - 1);

        int res = 0;
        for (int i = 1; i < arrays.size(); i++) {
            int sizeN = arrays.get(i).size();
            res = Math.max(res, maxVal - arrays.get(i).get(0));
            res = Math.max(res, arrays.get(i).get(sizeN - 1) - minVal);
            minVal = Math.min(minVal, arrays.get(i).get(0));
            maxVal = Math.max(maxVal, arrays.get(i).get(sizeN - 1));
        }
        return res;
    }

    public static boolean confusingNumber(int n) {
        Map<Integer, Integer> rotateMap = new HashMap<>();
        rotateMap.put(6, 9);
        rotateMap.put(9, 6);
        rotateMap.put(0, 0);
        rotateMap.put(1, 1);
        rotateMap.put(8, 8);
        int res = 0;
        int oldN = n;
        while (n > 0) {
            int num = n % 10;
            n = n / 10;
            if (!rotateMap.containsKey(num)) {
                return false;
            }
            num = rotateMap.get(num);
            res = res * 10 + num;
        }
        return res != oldN;
    }

    public String mergeAlternately(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int i = 0;
        int j = 0;
        StringBuilder sb = new StringBuilder();
        while (i < len1 || j < len2) {
            if (i < len1) {
                sb.append(word1.charAt(i));
                i++;
            }
            if (j < len2) {
                sb.append(word2.charAt(j));
                j++;
            }
        }
        return sb.toString();
    }


//    public static int trap(int[] height) {
//        int maxLeft = 0;
//        int maxRight = 0;
//        int res = 0;
//        int left = 1;
//        int right = height.length - 2;
//        while (left <= right) {
//            if (height[left - 1] < height[right + 1]) {
//                maxLeft = Math.max(maxLeft, height[left - 1]);
//                if (maxLeft > height[left]) {
//                    res = res + maxLeft - height[left];
//                }
//                left++;
//            } else {
//                maxRight = Math.max(maxRight, height[right + 1]);
//                if (maxRight > height[right]) {
//                    res = res + maxRight - height[right];
//                }
//                right--;
//            }
//        }
//        return res;
//    }


    public int climbStairs(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return n;
        }
        int dp1 = 0;
        int dp2 = 1;
        int dpn = 1;
        for (int i = 2; i < n; i++) {
            dpn = dp1 + dp2;
            dp1 = dp2;
            dp2 = dpn;
        }
        return dpn;
    }

    int coinChange(int[] coins, int amount) {
        // 题目要求的最终结果是 dp(amount)
        return dp(coins, amount);
    }

    // 定义：要凑出金额 n，至少要 dp(coins, n) 个硬币
    int dp(int[] coins, int amount) {
        // base case
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 计算子问题的结果
            int subProblem = dp(coins, amount - coin);
            // 子问题无解则跳过
            if (subProblem == -1) continue;
            // 在子问题中选择最优解，然后加一
            res = Math.min(res, subProblem + 1);
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public static String longestPalindrome(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length() - 1; i++) {
            dp[i][i] = true;
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
        }
        int l = 0;
        int r = 0;
        int maxLen = 1;
        for (int right = 1; right < s.length(); right++) {
            for (int left = 0; left < s.length() - 1; left++) {
                boolean b = s.charAt(left) == s.charAt(right);
                if (b && right - left <= 1) {
                    dp[left][right] = true;
                } else if (b) {
                    dp[left][right] = dp[left + 1][right - 1];
                }
                if (dp[left][right]) {
                    int len = right - left + 1;
                    if (len > maxLen) {
                        r = right;
                        l = left;
                        maxLen = len;
                    }
                }
            }
        }
        return s.substring(l, r + 1);
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int last = m + n - 1;

        while (j >= 0 && i >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[last] = nums1[i];
                last--;
                i--;
            } else {
                nums1[last] = nums2[j];
                last--;
                j--;
            }
        }
        while (j >= 0) {
            nums1[last] = nums2[j];
            last--;
            j--;
        }

    }


    public static List<Integer> majorityElement(int[] nums) {
        int num1 = 0;
        int count1 = 0;
        int num2 = 0;
        int count2 = 0;
        int len3 = nums.length / 3;
        for (int i = 0; i < nums.length; i++) {
            if (count1 > 0 && num1 == nums[i]) {
                count1++;
            } else if (count2 > 0 && num2 == nums[i]) {
                count2++;
            } else if (count1 == 0) {
                num1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                num2 = nums[i];
                count2 = 1;
            } else {
                count2--;
                count1--;
            }
        }

        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == num1) {
                count1++;
            }
            if (num == num2) {
                count2++;
            }
        }
        List<Integer> res = new ArrayList<>();
        if (count1 > len3) {
            res.add(num1);
        }
        if (count2 > len3) {
            res.add(num2);
        }
        return res;

    }

    public static void rotate(int[] nums, int k) {
        reverse(nums, 0, nums.length - 1);
        int split = k % nums.length;
        reverse(nums, 0, split - 1);
        reverse(nums, split, nums.length - 1);
    }

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }


    public static boolean canJump(int[] nums) {
        int maxStep = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxStep) {
                return false;
            }
            maxStep = Math.max(nums[i] + i, maxStep);
            if (maxStep >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    public static int trap(int[] height) {
        int length = height.length;
        int[] maxLeft = new int[length];
        int[] maxRight = new int[length];
        maxRight[length - 1] = height[length - 1];
        maxLeft[0] = height[0];
        for (int i = 1; i < length; i++) {
            maxLeft[i] = Math.max(height[i], maxLeft[i - 1]);
            maxRight[length - i - 1] = Math.max(maxRight[length - i], height[length - i - 1]);
        }
        int res = 0;
        for (int i = 0; i < length; i++) {
            int min = Math.min(maxLeft[i], maxRight[i]);
            if (min > height[i]) {
                res = res + min - height[i];
            }
        }
        return res;
    }

    public static int jump2(int[] nums) {
        int start = 0;
        int end = 0;
        int count = 0;
        while (end < nums.length - 1) {
            int num = nums[start];
            int maxPos = 0;
            for (int i = start; i <= end; i++) {
                maxPos = Math.max(i + nums[i], maxPos);
            }
            start = end + 1;
            end = maxPos;
            count++;
        }
        return count;

    }

    public static int hIndex(int[] citations) {
        Arrays.sort(citations);
        int h = 0;
        for (int i = 0; i < citations.length; i++) {
            // 至少有gte篇文章引用次数大于citations[i]。
            int gte = citations.length - i;
            int min = Math.min(citations[i], gte);
            h = Math.max(min, h);
        }
        return h;
    }

    public static int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        left[0] = 1;
        right[nums.length - 1] = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            left[i + 1] = left[i] * nums[i];
            right[nums.length - i - 2] = right[nums.length - i - 1] * nums[nums.length - i - 1];
        }

        for (int i = 0; i < nums.length; i++) {
            ans[i] = left[i] * right[i];
        }
        return ans;
    }


    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int i = 0;
        while (i < n) {
            int sumOfGas = 0, sumOfCost = 0;
            int cnt = 0;
            while (cnt < n) {
                int j = (i + cnt) % n;
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                if (sumOfCost > sumOfGas) {
                    break;
                }
                cnt++;
            }
            if (cnt == n) {
                return i;
            } else {
                i = i + cnt + 1;
            }
        }
        return -1;
    }

    public static int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int res = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            Integer a = map.get(s.charAt(i));
            Integer b = map.get(s.charAt(i + 1));
            if (a >= b) {
                res = res + a;
            } else {
                res = res - a;
            }
        }
        res = res + map.get(s.charAt(s.length() - 1));
        return res;

    }

    public String intToRoman(int num) {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "I");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(40, "XL");
        map.put(50, "L");
        map.put(90, "XC");
        map.put(100, "C");
        map.put(400, "CD");
        map.put(500, "D");
        map.put(900, "CM");
        map.put(1000, "M");

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            if (num > 1000) {
                sb.append(map.get(1000));
                num = num - 1000;
            } else if (num >= 900) {
                sb.append(map.get(900));
                num = num - 900;
            } else if (num >= 500) {
                sb.append(map.get(500));
                num = num - 500;
            }else if (num >= 400) {
                sb.append(map.get(400));
                num = num - 400;
            }else if (num >= 100) {
                sb.append(map.get(100));
                num = num - 100;
            }else if (num >= 90) {
                sb.append(map.get(90));
                num = num - 90;
            }else if (num >= 50) {
                sb.append(map.get(50));
                num = num - 50;
            }else if (num >= 40) {
                sb.append(map.get(40));
                num = num - 40;
            }else if (num >= 10) {
                sb.append(map.get(10));
                num = num - 10;
            }else if (num >= 9) {
                sb.append(map.get(9));
                num = num - 9;
            }else if (num >= 5) {
                sb.append(map.get(5));
                num = num - 5;
            }else if (num >= 4) {
                sb.append(map.get(4));
                num = num - 4;
            }else if (num >= 1) {
                sb.append(map.get(1));
                num = num - 1;
            }
        }
        return sb.toString();
    }

    public int lengthOfLastWord(String s) {
        String trim = s.trim();
        return trim.length()-trim.lastIndexOf(" ")-1;
    }


    public static void main(String[] args) {
        System.out.println(romanToInt("LVIII"));
    }
}
