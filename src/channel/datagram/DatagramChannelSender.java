package channel.datagram;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * UDP IO
 */
public class DatagramChannelSender {

    public static void main(final String[] args) {

        final byte[] data = "This is a UDP Packet".getBytes();

        try (final DatagramChannel udpChannel = DatagramChannel.open()) {

            udpChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5266));
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            udpChannel.send(buffer, new InetSocketAddress(InetAddress.getLocalHost(), 5268));
            System.out.println("Data sent");
            buffer.clear();

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }
}
