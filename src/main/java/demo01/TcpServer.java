package demo01;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {
	public static void main(String[] args) throws IOException{
		//作为线程运行，接收来自客服的请求
		IoAcceptor acceptor=new NioSocketAcceptor();
		//赋值一个处理器  
		acceptor.setHandler(new TcpServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 2);
		acceptor.bind(new InetSocketAddress(10086));
		System.out.println("服务器端启动成功");
	}
}
 
	//处理来自客户端的请求，并过滤	
	class TcpServerHandler extends IoHandlerAdapter{
		    //当一个新连接建立时  ,由I/O processor thread 调用
		  public void sessionCreated(IoSession session) throws Exception {
			  System.out.println("连接被成功建立");
		  }
		  
		   //当连接打开时调用
		    public void sessionOpened(IoSession session) throws Exception {
		    	System.out.println("连接被打开了");
		    }
		    //当连接关闭时调用
		    public void sessionClosed(IoSession session) throws Exception {
		    	System.err.println("连接被关闭了");
		    }
		    //当链接进入空闲状态时调用
		    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		    	System.out.println("空闲中:"+session.getIdleCount(status));
		    }
		    //当实现IoHandler的类抛出异常时调用
		    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		       cause.printStackTrace(); //打印异常
		    }
		    //当接收到消息时调用
		    public void messageReceived(IoSession session, Object message) throws Exception {
		    		
		    		//接收客户端消息
		    		IoBuffer IoBuffer=(IoBuffer) message;
		    		byte[] byteArray=new byte[IoBuffer.limit()];
		    		IoBuffer.get(byteArray,0,IoBuffer.limit());
		    		System.out.println("[来自客户端的消息]："+new String(byteArray,"UTF-8"));
		    		
		    		//发送消息至客户端
		    		byte[] responseByteArray = "你好".getBytes("UTF-8");
		    		IoBuffer responseIoBuffer = IoBuffer.allocate(responseByteArray.length);
		    		responseIoBuffer.put(responseByteArray);
		    		responseIoBuffer.flip();
		    		session.write(responseIoBuffer);
		    		
		    }
		    //当一个消息被(IoSession#write)发送过调用
		    public void messageSent(IoSession session, Object message) throws Exception {
		    	
		    }
    }
