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
            } else if (num >= 400) {
                sb.append(map.get(400));
                num = num - 400;
            } else if (num >= 100) {
                sb.append(map.get(100));
                num = num - 100;
            } else if (num >= 90) {
                sb.append(map.get(90));
                num = num - 90;
            } else if (num >= 50) {
                sb.append(map.get(50));
                num = num - 50;
            } else if (num >= 40) {
                sb.append(map.get(40));
                num = num - 40;
            } else if (num >= 10) {
                sb.append(map.get(10));
                num = num - 10;
            } else if (num >= 9) {
                sb.append(map.get(9));
                num = num - 9;
            } else if (num >= 5) {
                sb.append(map.get(5));
                num = num - 5;
            } else if (num >= 4) {
                sb.append(map.get(4));
                num = num - 4;
            } else if (num >= 1) {
                sb.append(map.get(1));
                num = num - 1;
            }
        }
        return sb.toString();
    }

    public int lengthOfLastWord(String s) {
        String trim = s.trim();
        return trim.length() - trim.lastIndexOf(" ") - 1;
    }

    public static String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        List<StringBuilder> resList = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            resList.add(new StringBuilder());
        }
        // 从上往下
        boolean isAdd = true;
        int currentRow = 0;
        for (int i = 0; i < s.length(); i++) {
            resList.get(currentRow).append(s.charAt(i));
            if (isAdd) {
                currentRow++;
            } else {
                currentRow--;
            }
            if (currentRow == numRows - 1 || currentRow == 0) {
                isAdd = !isAdd;
            }
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder stringBuilder : resList) {
            res.append(stringBuilder);
        }

        return res.toString();
    }

    public static int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) {
            return -1;
        }
        List<Integer> indexFirst = new ArrayList<>();

        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                indexFirst.add(i);

            }
        }
        for (Integer i : indexFirst) {
            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i) == needle.charAt(j)) {
                    if (j == needle.length() - 1) {
                        return i - j;
                    }
                    i++;
                } else {
                    break;
                }
                if (i >= haystack.length()) {
                    break;
                }
            }
        }
        return -1;
    }

    private void getNext(String s, int[] next) {
        int i = 0;
        next[0] = -1;
        int j = -1;
        while (i < s.length()) {
            if (j == -1 || s.charAt(i) == s.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        int tempLen = 0;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (tempLen + word.length() + temp.size() > maxWidth) {
                res.add(buildStr(temp, tempLen, maxWidth));
                temp = new ArrayList<>();
                temp.add(word);
                tempLen = word.length();
            } else {
                temp.add(word);
                tempLen = tempLen + word.length();
            }
        }
        if (!temp.isEmpty()) {
            res.add(buildStrLeft(temp, tempLen, maxWidth));
        }
        return res;
    }

    private static String buildStrLeft(List<String> temp, int tempLen, int maxWidth) {
        int remained = maxWidth - tempLen;
        if (temp.size() == 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < remained; i++) {
                sb.append(" ");
            }
            return temp.get(0) + sb;
        }

        for (int i = 0; i < temp.size() - 1; i++) {
            temp.set(i, temp.get(i) + " ");
            remained--;
        }

        if (remained > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < remained; i++) {
                sb.append(" ");
            }
            temp.add(sb.toString());
        }
        return transform2Str(temp);
    }

    private static String buildStr(List<String> temp, int tempLen, int maxWidth) {
        int remained = maxWidth - tempLen;
        if (temp.size() == 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < remained; i++) {
                sb.append(" ");
            }
            return temp.get(0) + sb;
        }

        while (remained > 0) {
            for (int i = 0; i < temp.size() - 1; i++) {
                temp.set(i, temp.get(i) + " ");
                remained--;
                if (remained == 0) {
                    return transform2Str(temp);
                }
            }
        }
        return transform2Str(temp);
    }

    private static String transform2Str(List<String> temp) {
        StringBuilder sb = new StringBuilder();
        for (String s : temp) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static boolean isPalindrome(String s) {
        s = s.toLowerCase();
        int i = 0;
        int j = s.length() - 1;
        A:
        while (i <= j && i < s.length() && j >= 0) {
            while (!isChar(s.charAt(i))) {
                i++;
                if (i >= s.length()) {
                    break A;
                }
            }
            while (!isChar(s.charAt(j))) {
                j--;
                if (j < 0) {
                    break A;
                }
            }
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return j <= i;
    }

    private static boolean isChar(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    public static boolean isSubsequence(String s, String t) {
        t = " " + t;
        int[][] dp = new int[t.length()][26];
        for (int i = 0; i < 26; i++) {
            int nextPos = -1;
            for (int j = t.length() - 1; j >= 0; j--) {
                dp[j][i] = nextPos;
                if (t.charAt(j) == i + 'a') {
                    nextPos = j;
                }
            }
        }

        int currentTIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            if (dp[currentTIndex][s.charAt(i) - 'a'] == -1) {
                return false;
            } else {
                currentTIndex = dp[currentTIndex][s.charAt(i) - 'a'];
            }
        }
        return true;

    }

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                return new int[]{left + 1, right + 1};
            }
        }
        return new int[]{};

    }

    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxAr = 0;
        while (left < right) {
            maxAr = Math.max((right - left) * Math.min(height[left], height[right]), maxAr);
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return maxAr;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            A:
            while (left < right) {
                int res = nums[left] + nums[right] + nums[i];
                if (res == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left + 1 < right && nums[left + 1] == nums[left]) {
                        left++;
                    }
                    while (right - 1 > left && nums[right - 1] == nums[right]) {
                        right--;
                    }
                    left++;
                    right--;
                    continue;
                }
                if (res > 0) {
                    int temp = nums[right];
                    right--;
                    while (temp == nums[right]) {
                        if (right > left) {
                            right--;
                        } else {
                            break A;
                        }
                    }
                } else {
                    int temp = nums[left];
                    left++;
                    while (temp == nums[left]) {
                        if (left < right) {
                            left++;
                        } else {
                            break A;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int min = Integer.MAX_VALUE;
        int total = 0;

        while (right < nums.length) {
            total = total + nums[right];
            right++;
            while (total >= target) {
                min = Math.min(min, right - left);
                total = total - nums[left];
                left++;
            }
        }
        return min;

    }

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charMap = new HashMap<>();
        charMap.put(s.charAt(0), 0);
        int maxLen = 0;
        int left = 0;
        int right = 1;

        while (right < s.length()) {
            Integer i = charMap.get(s.charAt(right));
            if (i != null && left <= i) {
                maxLen = Math.max(right - left, maxLen);
                left = i + 1;
            }
            charMap.put(s.charAt(right), right);
            right++;
        }
        return maxLen;
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> cachedMap = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            char key = magazine.charAt(i);
            int count = cachedMap.computeIfAbsent(key, temp -> 0);
            count++;
            cachedMap.put(key, count);
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            char key = ransomNote.charAt(i);
            if (cachedMap.containsKey(key)) {
                Integer count = cachedMap.get(key);
                count--;
                if (count == 0) {
                    cachedMap.remove(key);
                } else {
                    cachedMap.put(key, count);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> sTmap = new HashMap<>();
        Map<Character, Character> tSmap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character other = sTmap.get(s.charAt(i));
            if (other == null) {
                Character s1 = tSmap.get(t.charAt(i));
                if (s1 != null && s1 == s.charAt(i)) {
                    sTmap.put(s.charAt(i), t.charAt(i));
                    tSmap.put(t.charAt(i), s.charAt(i));
                } else if (s1 == null) {
                    sTmap.put(s.charAt(i), t.charAt(i));
                    tSmap.put(t.charAt(i), s.charAt(i));
                } else {
                    return false;
                }

            } else {
                if (other != t.charAt(i)) {
                    return false;
                }
            }
        }
        return true;

    }

    public static String decodeString(String s) {
        int k = 0;
        StringBuilder res = new StringBuilder();
        Deque<Integer> kstack = new ArrayDeque<>();
        Deque<StringBuilder> restack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '[') {
                kstack.push(k);
                k = 0;
                restack.push(res);
                res = new StringBuilder();
            } else if (c == ']') {
                int n = kstack.pop();
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    temp.append(res);
                }
                res = restack.pop().append(temp);
            } else if (c >= '0' && c <= '9') {
                k = k * 10 + c - '0';
            } else {
                res.append(c);
            }
        }
        return res.toString();

    }

    public static boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        char[] charArray = pattern.toCharArray();
        if (words.length != charArray.length) {
            return false;
        }
        Map<Character, String> map = new HashMap<>();
        Map<String, Character> reversemap = new HashMap<>();
        for (int i = 0; i < charArray.length; i++) {
            String word = map.get(charArray[i]);
            Character character = reversemap.get(words[i]);
            if (word == null && character == null) {
                map.put(charArray[i], words[i]);
                reversemap.put(words[i], charArray[i]);
            } else {
                if (word == null || character == null) {
                    return false;
                }
                if (charArray[i] != character) {
                    return false;
                }
                if (!word.equals(words[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) {
            return combinations;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
        return combinations;
    }

    public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }


    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 因为头节点有可能发生变化，使用虚拟头节点可以避免复杂的分类讨论
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode pre = dummyNode;
        // 第 1 步：从虚拟头节点走 left - 1 步，来到 left 节点的前一个节点
        // 建议写在 for 循环里，语义清晰
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        // 第 2 步：从 pre 再走 right - left + 1 步，来到 right 节点
        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }

        // 第 3 步：切断出一个子链表（截取链表）
        ListNode leftNode = pre.next;
        ListNode curr = rightNode.next;

        // 注意：切断链接
        pre.next = null;
        rightNode.next = null;

        // 第 4 步：同第 206 题，反转链表的子区间
        reverseLinkedList(leftNode);

        // 第 5 步：接回到原来的链表中
        pre.next = rightNode;
        leftNode.next = curr;
        return dummyNode.next;
    }

    private void reverseLinkedList(ListNode head) {
        // 也可以使用递归反转一个链表
        ListNode pre = null;
        ListNode cur = head;

        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode res = new ListNode();
        res.next = head;
        ListNode right = res;
        for (int i = 0; i < n; i++) {
            right = right.next;
        }
        ListNode left = res;
        while (right.next != null) {
            left = left.next;
            right = right.next;
        }
        // 这时候left在n-1处。
        left.next = left.next.next;

        return res.next;
    }

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Integer i1 = sMap.computeIfAbsent(s.charAt(i), key -> 0);
            i1++;
            sMap.put(s.charAt(i), i1);
        }
        for (int i = 0; i < t.length(); i++) {
            Integer i1 = tMap.computeIfAbsent(t.charAt(i), key -> 0);
            i1++;
            tMap.put(t.charAt(i), i1);
        }
        Set<Map.Entry<Character, Integer>> entries = sMap.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            Character sKey = entry.getKey();
            Integer tValue = tMap.getOrDefault(sKey, 0);
            if (!entry.getValue().equals(tValue)) {
                return false;
            }
        }
        return true;

    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int maxLeft = maxDepth(root.left);
        int maxRight = maxDepth(root.right);
        maxRes = Math.max(maxLeft + maxRight, maxRes);
        return Math.max(maxLeft, maxRight) + 1;
    }


    int maxRes = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return maxRes;
    }

    public TreeNode invertTree(TreeNode root) {
        invert(root);
        return root;
    }

    private void invert(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invert(root.left);
        invert(root.right);
    }

    boolean isSame = true;

    public boolean isSameTree(TreeNode p, TreeNode q) {
        isSame(p, q);
        return isSame;

    }

    public void isSame(TreeNode p, TreeNode q) {
        if (!isSame) {
            return;
        }
        if (p == null || q == null) {
            if (p == null && q != null) {
                isSame = false;
                return;
            }
            if (p != null && q == null) {
                isSame = false;
                return;
            }
            return;
        }
        if (p.val != q.val) {
            isSame = false;
            return;
        }
        isSame(p.left, q.left);
        isSame(p.right, q.right);
    }

    public static boolean isSymmetric(TreeNode root) {
        LinkedList<TreeNode> twoWayQueue = new LinkedList<>();
        twoWayQueue.offer(root.left);
        twoWayQueue.offer(root.right);

        while (!twoWayQueue.isEmpty()) {
            int size = twoWayQueue.size();
            Deque<TreeNode> savedLastTree = new ArrayDeque<>();
            Queue<TreeNode> firstQueue = new LinkedList<>();

            for (int i = 0; i < size / 2; i++) {
                TreeNode first = twoWayQueue.pollFirst();
                TreeNode pollLast = twoWayQueue.pollLast();
                if (!isValSame(first, pollLast)) {
                    return false;
                }
                if (first != null) {
                    firstQueue.offer(first);
//                    twoWayQueue.offer(first.left);
//                    twoWayQueue.offer(first.right);
                }
                if (pollLast != null) {
                    savedLastTree.push(pollLast);
                }
            }
            while (!firstQueue.isEmpty()) {
                TreeNode treeNode = firstQueue.poll();
                twoWayQueue.add(treeNode.left);
                twoWayQueue.add(treeNode.right);
            }
            while (!savedLastTree.isEmpty()) {
                TreeNode treeNode = savedLastTree.pop();
                twoWayQueue.add(treeNode.left);
                twoWayQueue.add(treeNode.right);
            }
        }
        return true;
    }

    private static boolean isValSame(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        return left != null && right != null && left.val == right.val;
    }

    private static TreeNode buildTreeNode(int[] list) {
        int currentIndex = 0;

        TreeNode rootNode = new TreeNode();
        rootNode.val = list[currentIndex];
        currentIndex++;
        Queue<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.add(rootNode);
        while (!treeNodes.isEmpty()) {
            TreeNode treeNode = treeNodes.poll();
            treeNode.left = new TreeNode(list[currentIndex]);
            treeNodes.offer(treeNode.left);
            currentIndex++;
            if (currentIndex == list.length) {
                break;
            }
            treeNode.right = new TreeNode(list[currentIndex]);
            treeNodes.offer(treeNode.right);
            currentIndex++;
            if (currentIndex == list.length) {
                break;
            }
        }
        return rootNode;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        // 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
        //  输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            List<String> stringList = map.computeIfAbsent(sortStr(str), k -> new ArrayList<>());
            stringList.add(str);
        }
        return new ArrayList<>(map.values());
    }

    private String sortStr(String str) {
        char[] charArray = str.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }


    public static ListNode deleteDuplicates(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode res = new ListNode();
        res.next = head;

        ListNode leftPoint = head;
        ListNode rightPoint = head.next;
        Set<Integer> shouldRemovedVal = new HashSet<>();

        while (rightPoint != null) {
            if ((leftPoint.val != rightPoint.val) && (leftPoint.next == rightPoint)) {
                leftPoint = leftPoint.next;
                rightPoint = rightPoint.next;
            } else if (leftPoint.val != rightPoint.val) {
                leftPoint.next = rightPoint;
                leftPoint = leftPoint.next;
                rightPoint = rightPoint.next;
            } else {
                shouldRemovedVal.add(rightPoint.val);
                rightPoint = rightPoint.next;
            }
        }
        if (leftPoint.next != null && leftPoint.next.val == leftPoint.val) {
            leftPoint.next = null;
        }
        leftPoint = res;
        rightPoint = res.next;
        while (rightPoint != null) {
            if (shouldRemovedVal.contains(rightPoint.val)) {
                leftPoint.next = rightPoint.next;
                rightPoint = rightPoint.next;
            } else {
                leftPoint = leftPoint.next;
                rightPoint = rightPoint.next;
            }
        }
        return res.next;
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int size = 1;
        ListNode tail = head;

        while (tail.next != null) {
            size++;
            tail = tail.next;
        }
        tail.next = head;

        int shouldMove = size - k % size;

        for (int i = 0; i < shouldMove - 1; i++) {
            head = head.next;
        }

        ListNode res = head.next;

        head.next = null;

        return res;
    }

    public ListNode partition(ListNode head, int x) {

        ListNode lessThenX = new ListNode();
        ListNode left = lessThenX;

        ListNode gteX = new ListNode();
        ListNode right = gteX;

        while (head != null) {
            if (head.val < x) {
                lessThenX.next = head;
                head = head.next;
                lessThenX = lessThenX.next;
                lessThenX.next = null;
            } else {
                gteX.next = head;
                head = head.next;
                gteX = gteX.next;
                gteX.next = null;
            }
        }
        lessThenX.next = right.next;

        return left.next;

    }

//    public Node connect(Node root) {
//        traverse(root.left,root.right);
//        return root;
//    }
//
//    public void traverse(Node leftNode, Node rightNode) {
//        if (leftNode == null || rightNode == null) {
//            return;
//        }
//        leftNode.next = rightNode;
//        traverse(leftNode.left,leftNode.right);
//        traverse(rightNode.left,rightNode.right);
//        traverse(leftNode.right,rightNode.left);
//
//    }


//    private static TreeNode buildTreeNode(int [] list){
//        TreeNode treeNode = new TreeNode();
//        
//    }

    private static ListNode buildListNode(int[] nums) {
        ListNode res = new ListNode();
        ListNode head = new ListNode(nums[0]);
        res.next = head;
        for (int i = 1; i < nums.length; i++) {
            head.next = new ListNode(nums[i]);
            head = head.next;
        }
        return res.next;
    }

    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        int maxVal = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > maxVal) {
                index = i;
                maxVal = nums[i];
            }
        }
        TreeNode treeNode = new TreeNode(maxVal);

        int[] left = null;
        if (index != 0) {
            left = Arrays.copyOfRange(nums, 0, index);
        }
        treeNode.left = constructMaximumBinaryTree(left);
        int[] right = null;
        if (index + 1 != nums.length) {
            right = Arrays.copyOfRange(nums, index + 1, nums.length);
        }
        treeNode.right = constructMaximumBinaryTree(right);
        return treeNode;
    }


    public TreeNode buildTreePre(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        return build(inorderIndexMap, preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        return buildPost(inorderIndexMap, postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private static TreeNode buildPost(Map<Integer, Integer> inorderIndexMap, int[] postorder, int postStart, int postEnd, int[] inorder, int inStart, int inEnd) {
        if (postStart > postEnd || inStart > inEnd || inEnd > inorder.length - 1 || postEnd > postorder.length - 1) {
            return null;
        }
        int rootval = postorder[postEnd];
        TreeNode node = new TreeNode(rootval);
        Integer inOrderRootIndex = inorderIndexMap.get(rootval);
        int leftLen = inOrderRootIndex - inStart;
        node.left = buildPost(inorderIndexMap, postorder, postStart, postStart + leftLen - 1, inorder, inStart, inOrderRootIndex - 1);
        node.right = buildPost(inorderIndexMap, postorder, postStart + leftLen, postEnd - 1, inorder, inOrderRootIndex + 1, inEnd);
        return node;
    }

    private TreeNode build(Map<Integer, Integer> inorderIndexMap, int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd || inEnd > inorder.length - 1 || preEnd > preorder.length - 1) {
            return null;
        }
        int rootval = preorder[preStart];
        TreeNode node = new TreeNode(rootval);
        Integer inOrderRootIndex = inorderIndexMap.get(rootval);
        int leftLen = inOrderRootIndex - inStart;
        node.left = build(inorderIndexMap, preorder, preStart + 1, preStart + leftLen, inorder, inStart, inOrderRootIndex - 1);
        node.right = build(inorderIndexMap, preorder, preStart + leftLen + 1, preEnd, inorder, inOrderRootIndex + 1, inEnd);
        return node;
    }

    public boolean isHappy(int n) {
        int slow = squalEveryNum(n);
        int fast = squalEveryNum(squalEveryNum(n));
        while (slow != fast) {
            slow = squalEveryNum(slow);
            fast = squalEveryNum(squalEveryNum(fast));
        }
        return slow == 1;
    }

    public int squalEveryNum(int n) {
        int res = 0;
        while (n > 0) {
            int s = n % 10;
            n = n / 10;
            res = res + s * s;
        }
        return res;
    }

//    public Node connect(Node root) {
//        Node res = root;
//        if (root == null) {
//            return root;
//        }
//        Node cur = root;
//        while (cur != null) {
//            Node head = new Node(-1);
//            Node tail = head;
//            for (Node i = cur; i != null; i = i.next) {
//                if (i.left != null) {
//                    tail.next = i.left;
//                    tail = tail.next;
//                }
//                if (i.right != null) {
//                    tail.next = i.right;
//                    tail = tail.next;
//                }
//            }
//            cur = head.next;
//        }
//        return res;
//
//    }

    TreeNode pre = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.right);
        flatten(root.left);
        root.right = pre;
        root.left = null;
        pre = root;

    }

    boolean hasPath = false;

    public boolean hasPathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return hasPath;
    }

    public void dfs(TreeNode node, int targetSum) {
        if (hasPath) {
            return;
        }
        if (node.left == null && node.right == null) {
            if (node.val == targetSum) {
                hasPath = true;
                return;
            }
        }
        if (node.left != null) {
            dfs(node.left, targetSum - node.val);
        }
        if (node.right != null) {
            dfs(node.right, targetSum - node.val);
        }
    }

    List<Integer> sumNumbersRes = new ArrayList<>();

    public int sumNumbers(TreeNode root) {
        traSumNumbers(root, 0);
        int res = 0;
        for (Integer sumNumbersRe : sumNumbersRes) {
            res += sumNumbersRe;
        }
        return res;
    }

    private void traSumNumbers(TreeNode node, int sum) {
        sum = sum * 10 + node.val;
        if (node.left == null && node.right == null) {
            sumNumbersRes.add(sum);
            return;
        }
        if (node.left != null) {
            traSumNumbers(node.left, sum);
        }
        if (node.right != null) {
            traSumNumbers(node.right, sum);
        }
    }

    public static int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int level = 0;
        TreeNode temp = root;
        while (temp.left != null) {
            temp = temp.left;
            level++;
        }
        temp = root;
        int low = 1 << level;
        int high = (1 << (level + 1)) - 1;
        while (low < high) {
            int mid = (high - low + 1) / 2 + low;
            if (existNode(temp, level, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    private static boolean existNode(TreeNode node, int level, int num) {
        int bits = 1 << (level - 1);

        while (bits > 0) {
            if ((bits & num) == 0) {
                node = node.left;
            } else {
                node = node.right;
            }
            bits = bits >> 1;
        }
        return node != null;
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        LinkedList<TreeNode> pTrace = new LinkedList<>();
        LinkedList<TreeNode> qTrace = new LinkedList<>();
        findTrace(pTrace, root, p);
        findTrace(qTrace, root, q);

        while ((!pTrace.isEmpty()) && (!qTrace.isEmpty())) {
            TreeNode peekP = pTrace.peek();
            TreeNode peekQ = qTrace.peek();
            if (qTrace.contains(peekP)) {
                return peekP;
            }
            if (pTrace.contains(peekQ)) {
                return peekQ;
            }
            pTrace.poll();
            qTrace.poll();
        }
        return null;
    }

    private static boolean findTrace(LinkedList<TreeNode> pTrace, TreeNode root, TreeNode p) {
        if (root == null) {
            return false;
        }
        if (p == root) {
            pTrace.add(root);
            return true;
        }

        boolean a = findTrace(pTrace, root.left, p);
        if (a) {
            pTrace.add(root);
            return true;
        }

        boolean b = findTrace(pTrace, root.right, p);
        if (b) {
            pTrace.add(root);
        }
        return b;
    }

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        traCombine(res, path, n, k, 1);
        return res;
    }

    public static void traCombine(List<List<Integer>> res, LinkedList<Integer> path, int n, int k, int index) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        int last = n - (k - path.size()) + 1;
        for (int i = index; i <= last; i++) {
            path.addLast(i);
            traCombine(res, path, n, k, i + 1);
            path.removeLast();
        }
    }


    StringBuilder sb = new StringBuilder();

    public List<String> letterCombinations2(String digits) {
        if (digits == null || digits.equals("")) {
            return new ArrayList<>();
        }
        Map<Character, String> digitsMap = new HashMap<>();
        digitsMap.put('2', "abc");
        digitsMap.put('3', "def");
        digitsMap.put('4', "ghi");
        digitsMap.put('5', "jkl");
        digitsMap.put('6', "mno");
        digitsMap.put('7', "pqrs");
        digitsMap.put('8', "tuv");
        digitsMap.put('9', "wxyz");
        List<String> res = new ArrayList<>();
        traLetterCombinations(res, 0, digits, digitsMap);

        return res;


    }

    private void traLetterCombinations(List<String> res, int index, String digits, Map<Character, String> digitsMap) {
        if (sb.length() == digits.length()) {
            res.add(new String(sb));
            return;
        }
        char c = digits.charAt(index);
        String s = digitsMap.get(c);
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            traLetterCombinations(res, index + 1, digits, digitsMap);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        boolean[] used = new boolean[nums.length];
        List<Integer> path = new ArrayList<>();
        traPermute(res, nums, used, path);
        return res;

    }

    private void traPermute(List<List<Integer>> res, int[] nums, boolean[] used, List<Integer> path) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                path.add(nums[i]);
                traPermute(res, nums, used, path);
                used[i] = false;
                path.remove(path.size() - 1);
            }

        }

    }


    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        traCombinationSum(candidates, target, new ArrayList<>(), res, 0);
        return res;

    }

    private static void traCombinationSum(int[] candidates, int target, List<Integer> arr, List<List<Integer>> res, int index) {
        if (target == 0) {
            res.add(new ArrayList<>(arr));
            return;
        }
        if (target < 0) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (target - candidates[i] < 0) {
                break;
            }
            arr.add(candidates[i]);
            traCombinationSum(candidates, target - candidates[i], arr, res, i);
            arr.remove(arr.size() - 1);
        }
    }

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        traGenerateParenthesis(n, 1, 0, new StringBuilder("("), res);
        return res;
    }

    private static void traGenerateParenthesis(int n, int leftCount, int rightCount, StringBuilder stringBuilder, List<String> res) {
        if (leftCount == n && rightCount == n) {
            res.add(new String(stringBuilder));
        }
        if (rightCount < leftCount) {
            stringBuilder.append(')');
            traGenerateParenthesis(n, leftCount, rightCount + 1, stringBuilder, res);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        if (leftCount < n) {
            stringBuilder.append('(');
            traGenerateParenthesis(n, leftCount + 1, rightCount, stringBuilder, res);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }

    static boolean existWord = false;

    public static boolean exist(char[][] board, String word) {
        char c = word.charAt(0);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == c) {
                    boolean[][] used = new boolean[board.length][board[0].length];
                    used[i][j] = true;
                    traExistWord(i, j, 1, word, board, used);
                }
            }
        }
        return existWord;
    }

    private static void traExistWord(int i, int j, int index, String word, char[][] board, boolean[][] used) {
        if (existWord) {
            return;
        }
        if (index == word.length()) {
            existWord = true;
            return;
        }
        int iMaxIndex = board.length - 1;
        int jMaxIndex = board[0].length - 1;
        if (i < iMaxIndex&&board[i+1][j]==word.charAt(index)&&(!used[i+1][j])) {
            used[i+1][j] = true;
            traExistWord(i+1,j,index+1,word,board,used);
            used[i+1][j] = false;
        }
        if (j < jMaxIndex&&board[i][j+1]==word.charAt(index)&&(!used[i][j+1])) {
            used[i][j+1] = true;
            traExistWord(i,j+1,index+1,word,board,used);
            used[i][j+1] = false;
        }
        if (i > 0&&board[i-1][j]==word.charAt(index)&&(!used[i-1][j])) {
            used[i-1][j] = true;
            traExistWord(i-1,j,index+1,word,board,used);
            used[i-1][j] = false;
        }
        if (j > 0&&board[i][j-1]==word.charAt(index)&&(!used[i][j-1])) {
            used[i][j-1] = true;
            traExistWord(i,j-1,index+1,word,board,used);
            used[i][j-1] = false;
        }
    }


    public static void main(String[] args) {
//        TreeNode treeNode = buildTreeNode(new int[]{3, 5, 1, 6, 2, 0, 8, 7, 4});
//        lowestCommonAncestor(treeNode, treeNode.left, treeNode.right);
        boolean e  = exist(new char[][]{{'A','B','C','E'},{'S','F','Z','X'}},"ABCB");
        System.out.println();
    }
}
