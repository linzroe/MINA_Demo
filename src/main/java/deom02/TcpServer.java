package deom02;

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
			IoAcceptor acceptor=new NioSocketAcceptor();
			// 添加一个Filter，用于接收、发送的内容按照"\r\n"分割
			acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), "\r\n", "\r\n")));
			acceptor.setHandler(new TcpServerHandler());
			acceptor.bind(new InetSocketAddress(4515));
		 }
}

  class TcpServerHandler extends IoHandlerAdapter{
	   
	  public void exceptionCaught(IoSession session,Throwable cause) throws Exception{
		  cause.printStackTrace();
	  }
	  
	  //接收数据
	  public void messageReceived(IoSession session,Object message) throws Exception{
		  //接收来自客户端的消息 接收字符串
		  
		  String str=(String) message;
		  System.out.println("[来自客户端的消息]:"+str);
	  }
	  
	  public void sessionCreated(IoSession session) throws Exception{
		  System.out.println("调用请求");
	  }
	  
	  public void sessionCloased(IoSession session) throws Exception{
		  
		  System.out.println("关闭连接");
		  
	  }
	  
  }
