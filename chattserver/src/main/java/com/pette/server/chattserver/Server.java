package com.pette.server.chattserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.pette.server.chattserver.connection.ConnectionHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {
    private static final int PORT = 6484;

    public static void main(String[] args) throws IOException {
        final IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        acceptor.setHandler(new ConnectionHandler());
        acceptor.getSessionConfig().setReadBufferSize(1048576);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        InetSocketAddress socketAddress = new InetSocketAddress(PORT);
        acceptor.bind(socketAddress);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                acceptor.unbind();
            }
        });
    }
}
