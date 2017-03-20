package deom06;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {
			public static void main(String[] args) throws IOException{
				//接收
				IoAcceptor acceptor=new NioSocketAcceptor();
				//设置编码器解码器
				acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),"\r\n","\r\n")));
				//设置处理器    
				acceptor.setHandler(new TcpServerHandler());
				acceptor.bind(new InetSocketAddress(5454));
				
			}
}

	/**
	 * 处理所有IO事件，这个接口是过滤器最后所有的活动完成中心
	 * @author DELL
	 *
	 */
	class TcpServerHandler extends IoHandlerAdapter{
		
		
		/**
		 * 异常抛出
		 */
	    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			 cause.printStackTrace();
			
		}
	    
	    /**
	     * 接收消息，并处理
	     */
	    public void messageReceived(IoSession session, Object message) throws Exception {
	    	int counter = 1;

			// 第一次请求，创建session中的counter
			if(session.getAttribute("counter") == null) {
				session.setAttribute("counter", 1);
			} else {
				// 获取session中的counter，加1后再存入session
				counter = (Integer) session.getAttribute("counter");
				counter++;
				session.setAttribute("counter", counter);
			}

			String line = (String) message;
			System.out.println("第" + counter + "次请求:" + line);
	    }
		
		
	}
