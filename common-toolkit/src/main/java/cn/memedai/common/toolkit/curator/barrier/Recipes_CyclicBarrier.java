package cn.memedai.common.toolkit.curator.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2016/7/26.
 */
public class Recipes_CyclicBarrier {
    public static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("1号选手")));
        executor.submit(new Thread(new Runner("2号选手")));
        executor.submit(new Thread(new Runner("3号选手")));
        executor.shutdown();
    }



}
class Runner implements Runnable{
    private  String name;
    public  Runner(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " 准备好了。");
        try {
            Recipes_CyclicBarrier.barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(name + " 起跑！");
    }
}




































