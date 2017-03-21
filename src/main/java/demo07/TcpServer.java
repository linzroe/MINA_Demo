package demo07;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {
	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), "\r\n", "\r\n")));

		acceptor.setHandler(new ServerHandler());
		acceptor.bind(new InetSocketAddress(5454));
		
	}
}
