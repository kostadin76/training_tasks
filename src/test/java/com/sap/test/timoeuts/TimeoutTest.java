package com.sap.test.timoeuts;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


public class TimeoutTest {
	
	@Test(timeout=1000)
	public void testTimeout(){
		try{
			Thread.sleep(2000);
		} catch(InterruptedException ie){
			
		}
	}
	
	public static String log;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Ignore("")
    @Test
    public void testSleepForTooLong() throws Exception {
        log += "ran1";
        TimeUnit.SECONDS.sleep(100); // sleep for 100 seconds
    }

    @Ignore
    @Test
    public void testBlockForever() throws Exception {
        log += "ran2";
        latch.await(); // will block 
    }
}
