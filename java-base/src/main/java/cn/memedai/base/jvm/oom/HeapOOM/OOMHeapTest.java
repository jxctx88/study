package cn.memedai.base.jvm.oom.HeapOOM;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/5.
 *
 * 设置VM -Xms10m -Xmx100m -XX:+HeapDumpOnOutOfMemoryError
 */
public class OOMHeapTest {


    public static void main(String[] args) {
        oom();
    }

    private static void oom() {
        Map<String,Pilot> map = new HashMap<String,Pilot>();
        Object[] array = new Object[10000000];
        for (int i = 0; i < 1000000; i++) {
            String d = new Date().toString();
            Pilot p = new Pilot(d,i);
            map.put(i+"resen jiang",p);
            array[i]=p;
        }

    }

}
