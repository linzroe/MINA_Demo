package deom03;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {

	public static void main(String[] args) throws Exception{
		
			IoAcceptor acceptor=new NioSocketAcceptor();
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
			acceptor.setHandler(new TcpServerHandler());
			acceptor.bind(new InetSocketAddress(5454));
	}
}

	class TcpServerHandler extends IoHandlerAdapter{
			

		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {
			cause.printStackTrace();
		}

		// 接收到新的数据
		@Override
		public void messageReceived(IoSession session, Object message)
				throws Exception {

			String msg = (String) message;
			System.out.println("messageReceived:" + msg);

		}

		@Override
		public void sessionCreated(IoSession session) throws Exception {
			System.out.println("sessionCreated");
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			System.out.println("sessionClosed");
		}
	}