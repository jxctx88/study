package cn.memedai.common.toolkit.curator.barrier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by admin on 2016/7/26.
 * 使用Curator【DistributedBarrier】 实现分布式是Barrier
 */
public class Recipes_Barrier {
    static  String barrier_path = "/curator_recipes_barrier_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("99.48.66.13:2181").retryPolicy(new ExponentialBackoffRetry(1000,3)).sessionTimeoutMs
                    (5000).build();
    static DistributedBarrier barrier;

    public static void main(String[] args) throws Exception {
        client.start();
        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier = new DistributedBarrier(client,barrier_path);
                        System.out.println(Thread.currentThread().getName() + "号barrier设置");
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        System.out.println("启动。。。");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        barrier.removeBarrier();
    }

}

















