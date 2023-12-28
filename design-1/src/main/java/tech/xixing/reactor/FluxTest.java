package tech.xixing.reactor;

import reactor.core.publisher.Flux;

/**
 * @author liuzhifei
 * @date 2022/6/27 7:17 下午
 */
public class FluxTest {

    public static void main(String[] args) {
        Flux.just("hello world","print").subscribe(System.out::println);
        Flux.range(0,100).subscribe(System.out::println);
    }
}
