package cn.memedai.base.jvm.oom;

import java.nio.ByteBuffer;

/**
 * Created by admin on 2016/7/28.
 * 直接内存OOM
 */
public class DirectBufferOOM {

    public static void main(String[] args) {
        for (int i = 0; i < 1024; i++) {
            ByteBuffer.allocateDirect(1024*1024);
            System.out.println(i);
            //System.gc();
        }
    }
}
























