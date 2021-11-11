package legoApp.utils;


import java.util.concurrent.TimeUnit;

public class ThreadWait {


    public static void delay(int timeOut, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(timeOut));
        } catch (InterruptedException ex) {
            System.out.println (("driver.wait failed."));
        }
    }
}
