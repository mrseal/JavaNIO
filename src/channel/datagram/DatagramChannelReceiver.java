package channel.datagram;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * UDP IO
 */
public class DatagramChannelReceiver {

    public static void main(final String[] args) {

        try (final DatagramChannel udpChannel = DatagramChannel.open()) {

            udpChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5268));
            final ByteBuffer buffer = ByteBuffer.allocate(50);
            System.out.println("Start listening ...");
            udpChannel.receive(buffer);
            System.out.println("Received data: " + new String(buffer.array()));

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }
}
