package cn.memedai.common.toolkit.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by chengtx on 2016/7/22.
 */
public class SocketChannelDemo {

    public static void startClient() throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8999));
       // socketChannel.configureBlocking(false);

        String request = "hello ServerSocketChannel";
        ByteBuffer buf = ByteBuffer.wrap(request.getBytes("UTF-8"));
        socketChannel.write(buf);
        /*Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(sc.next().getBytes("UTF-8"));
            socketChannel.write(byteBuffer);
        }*/

        ByteBuffer rbuf = ByteBuffer.allocate(48);
        int size = socketChannel.read(rbuf);
        while ((size > 0)) {
            rbuf.flip();
            Charset charset = Charset.forName("UTF-8");
            System.out.println(charset.newDecoder().decode(rbuf));
            rbuf.clear();
            size = socketChannel.read(rbuf);
        }

        buf.clear();
        rbuf.clear();
        socketChannel.close();

        socketChannel.close();
    }

















    public static void main(String[] args) throws Exception {
        startClient();

    }

}
