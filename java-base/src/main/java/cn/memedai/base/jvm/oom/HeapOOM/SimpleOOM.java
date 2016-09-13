package cn.memedai.base.jvm.oom.HeapOOM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongxiong.cheng on 2016/9/12.
 * 设置VM -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 */
public class SimpleOOM {


    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<byte[]>();
        for(int i=0;i<9;i++){
            byte[] b = new byte[1024*1024];
            list.add(b);
        }
    }
}
