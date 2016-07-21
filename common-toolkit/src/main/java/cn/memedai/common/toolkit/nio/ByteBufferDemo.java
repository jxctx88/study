package cn.memedai.common.toolkit.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by chengtx on 2016/7/21.
 * 通过读取文件写到ByteBuffer里,然后再从ByteBuffer对象中获取数据,显示到控制台
 */
public class ByteBufferDemo {

    public static void main(String[] args) throws Exception {
        readFile("1.txt");
    }


    public static void readFile(String fileName) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName,"rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int size = fileChannel.read(byteBuffer);
        while(size>0){
            //把buyeBuffer从写模式,转变成读模式
            byteBuffer.flip();
            /*while(byteBuffer.remaining()>0) {
                // 调用getChar（）方法，char的字符长度为2个字节，而ByteBuffer的remaining长度为1时，强行获取两个字节抛出异常
                System.out.println(byteBuffer.getChar()+";"+byteBuffer.position()+";"+byteBuffer.remaining());
            }*/

            Charset charset = Charset.forName("UTF-8");
            System.out.println(charset.newDecoder().decode(byteBuffer));
            byteBuffer.clear();
            size = fileChannel.read(byteBuffer);
        }
        fileChannel.close();
        randomAccessFile.close();

    }

}





























