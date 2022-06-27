package tech.xixing.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author xixingya
 */
public class TestSPI {

    public static void main(String[] args) {
        ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);

        for (Log next : serviceLoader) {
            next.log("aaa");
        }
    }
}
