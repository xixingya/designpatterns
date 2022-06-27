package tech.xixing.spi;

/**
 * @author xixingya
 */
public class Log4j2 implements Log{
    @Override
    public void log(String log) {
        System.out.println("log4j2 print "+log);
    }
}
