package cn.memedai.base.jvm.memory;

import java.nio.ByteBuffer;

/**
 * Created by admin on 2016/7/28.
 * 直接内存申请慢，但是运行速度快，而堆内存申请快，但是运行慢
 */
public class AccessDirectBuffer {

    public void directAccess(){
        long starttime = System.currentTimeMillis();
        ByteBuffer b = ByteBuffer.allocateDirect(500);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                b.putInt(j);
            }
            b.flip();
            for (int j = 0; j < 99; j++) {
                b.getInt();
            }
            b.clear();
        }
        long endtime = System.currentTimeMillis();
        System.out.println("testDirectWirite:"+(endtime-starttime));
    }


    public void bufferAccess(){
        long starttime = System.currentTimeMillis();
        ByteBuffer b = ByteBuffer.allocate(500);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++)
                b.putInt(j);

            b.flip();
            for (int j = 0; j < 99; j++)
                b.getInt();

            b.clear();
        }
        long endtime = System.currentTimeMillis();
        System.out.println("testDirectWirite:"+(endtime-starttime));
    }

    public static void main(String[] args) {
        AccessDirectBuffer alloc = new AccessDirectBuffer();
        alloc.bufferAccess();
        alloc.directAccess();

        alloc.bufferAccess();
        alloc.directAccess();
    }
}
