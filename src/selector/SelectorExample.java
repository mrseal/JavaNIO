package selector;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class SelectorExample {

    public static void main(final String[] args) {
        DatagramChannel c1 = null;
        DatagramChannel c2 = null;
        Selector selector = null;

        try {
            final InetAddress host = InetAddress.getLocalHost();
            c1 = createDatagramChannel(host, 5120);
            c2 = createDatagramChannel(host, 5122);
            selector = Selector.open();
            c1.register(selector, SelectionKey.OP_READ);
            c2.register(selector, SelectionKey.OP_READ);

            final ByteBuffer buffer = ByteBuffer.allocate(30);
            System.out.println("start listening ...");

            while (true) {
                final int selected = selector.select();
                if (selected == 0) {
                    continue;
                }
                final Set<SelectionKey> keys = selector.selectedKeys();
                final Iterator<SelectionKey> itr = keys.iterator();
                while (itr.hasNext()) {
                    final SelectionKey key = itr.next();
                    if (key.isReadable()) {
                        final DatagramChannel c = (DatagramChannel) key.channel();
                        c.receive(buffer);
                        System.out.println("[" + c.getLocalAddress() + "] received: " + new String(buffer.array()));
                        buffer.clear();
                    }
                    itr.remove();
                }

            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            safeClose(c1, c2, selector);
        }
    }

    public static DatagramChannel createDatagramChannel(final InetAddress host, final int port) throws IOException {
        final DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(host, port));
        channel.configureBlocking(false);
        return channel;
    }

    private static void safeClose(final Closeable... closeables) {
        for (final Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
