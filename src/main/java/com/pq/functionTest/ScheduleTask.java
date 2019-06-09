package com.pq.functionTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleTask {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> sf = ses.schedule(()->System.out.println("5s later,task being done now"),5, TimeUnit.SECONDS);
        ses.shutdown();
    }
}
