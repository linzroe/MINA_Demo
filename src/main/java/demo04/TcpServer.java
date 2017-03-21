package demo04;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {

	public static void main(String[] args) throws Exception{
			
			IoAcceptor acceptor=new NioSocketAcceptor();
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyMinaEncoder(),new MyMinaDecoder()));
			acceptor.getSessionConfig().setReadBufferSize(2048);
			acceptor.setHandler(new TcpServerHandle());
			acceptor.bind(new InetSocketAddress(5454));
	}
}
			 
