package tech.xixing.spi;

/**
 * @author xixingya
 */
public class LogBack implements Log{
    @Override
    public void log(String log) {
        System.out.println("logback print "+log);
    }
}
