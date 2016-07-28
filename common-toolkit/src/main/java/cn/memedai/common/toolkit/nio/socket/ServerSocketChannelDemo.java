package cn.memedai.common.toolkit.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by chengtx on 2016/7/22.
 */
public class ServerSocketChannelDemo {


    public static void start() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8999));
        //serverSocketChannel.configureBlocking(false);

        while(true){

            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                int size = socketChannel.read(byteBuffer);
                while(size>0){
                    byteBuffer.flip();
                    Charset charset = Charset.forName("UTF-8");
                    System.out.println(charset.newDecoder().decode(byteBuffer));
                    size = socketChannel.read(byteBuffer);
                }
                socketChannel.close();
            }

        }


    }

    public static void main(String[] args) throws Exception {
        start();
    }


}












