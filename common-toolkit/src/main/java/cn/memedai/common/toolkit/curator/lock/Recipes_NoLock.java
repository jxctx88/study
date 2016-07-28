package cn.memedai.common.toolkit.curator.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2016/7/26.
 * 没有实现锁的生成订单案例
 */
public class Recipes_NoLock {

    public static void main(String[] args) {
        final CountDownLatch down = new CountDownLatch(1);
        for(int i = 0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        down.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNO = sdf.format(new Date());
                    System.err.println("生成的订单号是："+ orderNO);

                }
            }).start();
        }
        down.countDown();
    }

}






























