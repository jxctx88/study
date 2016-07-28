package cn.memedai.common.toolkit.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by chengtx on 2016/7/28.
 */
public class SelectorServerSocketChannelDemo {

    public static void main(String[] args) throws Exception {
        startServer();
    }

    public static void startServer() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8999));

        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        while (true){
            //每一个连接,只会select一次,之后就删除了
            int sellect = selector.select();

            //是否有可用的通道接入
            if(sellect>0){
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    if(selectionKey.isAcceptable()){
                        //获取数据
                        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                        ByteBuffer buf = ByteBuffer.allocate(40);
                        int size = socketChannel.read(buf);
                        while (size > 0){
                            buf.flip();
                            Charset charset = Charset.forName("UTF-8");
                            System.out.println(charset.newDecoder().decode(buf).toString());
                            size = socketChannel.read(buf);
                        }

                        buf.clear();


                        ByteBuffer response = ByteBuffer.wrap("您好! 我已经收到了您的请求!".getBytes("UTF-8"));
                        socketChannel.write(response);
                        socketChannel.close();
                        selector.selectedKeys().remove(selectionKey);
                    }
                }
            }
        }



    }


}


























