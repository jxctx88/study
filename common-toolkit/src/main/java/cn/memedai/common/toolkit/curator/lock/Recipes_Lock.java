package cn.memedai.common.toolkit.curator.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2016/7/26.
 * 使用Curator InterProcessMutex实现分布式锁的功能
 */
public class Recipes_Lock {

    static String lock_path = "/curator_recipes_lock_path";
   static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("99.48.66.13:2181").retryPolicy(new ExponentialBackoffRetry(1000,3)).sessionTimeoutMs
                    (5000).build();

    public static void main(String[] args) {


        client.start();
        final InterProcessMutex lock = new InterProcessMutex(client,lock_path);
        final CountDownLatch down = new CountDownLatch(1);
        for (int i=0;i<30;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        down.await();
                        lock.acquire();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNO = sdf.format(new Date());
                    System.out.println("生成的订单号是：" + orderNO);
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        down.countDown();

    }
}


































