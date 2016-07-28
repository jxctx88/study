package cn.memedai.common.toolkit.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Created by chengtx on 2016/7/28.
 */
public class AioServerDemo {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        startServer();
    }

    public static void startServer() throws Exception {
        AsynchronousServerSocketChannel asyServerSocketChannel = AsynchronousServerSocketChannel.open();
        asyServerSocketChannel.bind(new InetSocketAddress(8999));
        
        asyServerSocketChannel.accept(null,
                new CompletionHandler<AsynchronousSocketChannel, Object>() {
                    @Override
                    public void completed(AsynchronousSocketChannel result, Object attachment) {
                        try {
                            asyServerSocketChannel.accept(attachment,this);
                            operat(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        latch.countDown();
                    }
                });
        latch.await();

    }

    private static void operat(AsynchronousSocketChannel result) throws Exception {

        ByteBuffer buf = ByteBuffer.allocate(48);
        int size = result.read(buf).get();
        while(size>0){
            buf.flip();
            Charset charset = Charset.forName("UTF-8");
            System.out.println(charset.newDecoder().decode(buf).toString());
            size = result.read(buf).get();
        }
        result.close();
    }

}

























