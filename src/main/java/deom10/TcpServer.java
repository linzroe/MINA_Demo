package deom10;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {
	public static void main(String[] args) throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor(4); // 配置I/O processor thread线程数量
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter()); // 将TcpServerHandle中的业务逻辑拿到ExecutorFilter的线程池中执行
		acceptor.setHandler(new TcpServerHandle());
		acceptor.bind(new InetSocketAddress(8080));
	}
}
 	class TcpServerHandle extends IoHandlerAdapter{
 		@Override
 		public void messageReceived(IoSession session, Object message)
 				throws Exception {
 			// 假设这里有个变态的SQL要执行3秒
 			Thread.sleep(3000);
 		}
 	}
