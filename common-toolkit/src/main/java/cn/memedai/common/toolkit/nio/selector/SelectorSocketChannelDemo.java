package cn.memedai.common.toolkit.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by chengtx on 2016/7/28.
 */
public class SelectorSocketChannelDemo {

    public static void main(String[] args) throws Exception {
        startClient();
    }


    public static void startClient() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8999));
        //主要是设置成非阻塞
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);

        new Thread(new ClientThread(selector)).start();

        ByteBuffer byteBuffer = ByteBuffer.wrap("hell 我是客户端".getBytes("UTF-8"));
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

    }
}
















































