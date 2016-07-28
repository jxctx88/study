package cn.memedai.common.toolkit.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by chengtx on 2016/7/22.
 */
public class SocketChannelDemo {

    public static void startClient() throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8999));
       // socketChannel.configureBlocking(false);

        //String request = "hello ServerSocketChannel";
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(sc.next().getBytes("UTF-8"));
            socketChannel.write(byteBuffer);
        }

        //socketChannel.close();
    }

    public static void main(String[] args) throws Exception {
        startClient();
    }

}
