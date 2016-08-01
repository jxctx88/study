package cn.memedai.base.jvm;

/**
 * Created by admin on 2016/7/28.
 * 设置 VM options :-Xmx32m
 *
 */
public class SimpleArgs {

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("参数：" + (i + 1) + "=" + args[i]);
        }
        System.out.println("-Xmx:" + Runtime.getRuntime().maxMemory() / 1000 / 1000 + "M");
    }
}
