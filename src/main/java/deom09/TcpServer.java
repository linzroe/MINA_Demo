package deom09;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {
		public static void main(String[] args) {
			IoAcceptor acceptor=new NioSocketAcceptor();
			
			
			acceptor.setHandler(new tcpserverhandle());
		
		}
}
	
	class tcpserverhandle extends IoHandlerAdapter{
		@Override
		public void messageReceived(IoSession session, Object message)
				throws Exception {

			
			
			// 读操作省略...

			// 发送数据到客户端
			WriteFuture future = session.write("message");
			future.addListener(new IoFutureListener<WriteFuture>() {

				// write操作完成后调用的回调函数
				@Override
				public void operationComplete(WriteFuture future) {
					if(future.isWritten()) {
						System.out.println("write操作成功");
					} else {
						System.out.println("write操作失败");
					}
				}
			});
		}
		
		
	}
