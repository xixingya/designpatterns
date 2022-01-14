package tech.xixing.threads.wait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<User> list = new ArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            final User user = new User();
            user.age = (int) (Math.random() * 1000000);
            user.name="qaq";
            list.add(user);
        }

        long start = System.currentTimeMillis();
        Collections.sort(list);
        System.out.println(System.currentTimeMillis() - start);
    }

}


class User implements Comparable<User>{

    String name;

    int age;

    @Override
    public int compareTo(User o) {
        return o.age - age;
    }
}
