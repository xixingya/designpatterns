package tech.xixing.common;

import org.omg.SendingContext.RunTime;

import java.util.*;

/**
 * @author liuzhifei
 * @date 2022/2/18 4:58 下午
 */
public class AddAndAddAll {


    public static void main(String[] args) throws InterruptedException {

        Scanner scanner =new Scanner(System.in);

        scanner.next();
        Map<String,List<Integer>> map= new HashMap<>();
        for (int i = 0; i < 10; i++) {
            List<Integer> list =new ArrayList<>();
            for (int j = 0; j < 1000000; j++) {
                list.add(j);
            }
            map.put(String.valueOf(i),list);
        }
        scanner.next();

        Runtime r = Runtime.getRuntime();
        long startMem = r.freeMemory();

        //61MB
        List<Integer> integers = testLinked(map);

        long firstMem = r.freeMemory();
        System.out.println(firstMem-startMem);

        scanner.next();
        List<Integer> integers1 = testArray(map);
        long endMem = r.freeMemory();

        System.out.println(endMem-firstMem);


        System.out.println(integers.size());
        System.out.println(integers1.size());

        String next = scanner.next();

    }

    public static List<Integer> testArray(Map<String,List<Integer>> map){

        Collection<List<Integer>> values = map.values();
        List<Integer> test2 =new ArrayList<>(1000000);
        for (List<Integer> value : values) {
            test2.addAll(value);
        }
        return test2;
    }

    public static List<Integer> testLinked(Map<String,List<Integer>> map){

        List<Integer> test1 =new LinkedList<>();

        Collection<List<Integer>> values = map.values();
        for (List<Integer> value : values) {
            Iterator<Integer> iterator = value.iterator();
            while (iterator.hasNext()){
                Integer next = iterator.next();
                test1.add(next);
            }
        }
        return test1;
    }


}
