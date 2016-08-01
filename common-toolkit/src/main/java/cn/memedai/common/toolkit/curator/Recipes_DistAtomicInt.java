package cn.memedai.common.toolkit.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by admin on 2016/7/26.
 * 使用Curator DistributeAtomicInteger 实现分布式计数器
 */
public class Recipes_DistAtomicInt {
    static String distatomicint_path = "/curator_recipes_distatomicint_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("99.48.66.13:2181").retryPolicy(new ExponentialBackoffRetry(1000,3)).sessionTimeoutMs
                    (5000).build();

    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client,distatomicint_path,new
                RetryNTimes(3,1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println(rc.preValue()+":"+rc.postValue());
        System.out.println("Result: "+ rc.succeeded());
        System.out.println("eee");
    }
}





























