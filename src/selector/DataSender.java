package selector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * UDP IO
 */
public class DataSender {

    public static void main(final String[] args) {

        try (final DatagramChannel udpChannel = DatagramChannel.open()) {

            udpChannel.bind(null);
            final ByteBuffer buffer = ByteBuffer.allocate(30);
            buffer.put("Hello c1".getBytes());
            buffer.flip();
            udpChannel.send(buffer, new InetSocketAddress(InetAddress.getLocalHost(), 5120));
            buffer.clear();
            buffer.put("Hello c2".getBytes());
            buffer.flip();
            udpChannel.send(buffer, new InetSocketAddress(InetAddress.getLocalHost(), 5122));
            buffer.clear();
            System.out.println("Data sent");

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

}
