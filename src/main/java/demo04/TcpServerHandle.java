package demo04;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TcpServerHandle extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	// 接收到新的数据
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {

		// MyMinaDecoder将接收到的数据由IoBuffer转为String
		String msg = (String) message;
		System.out.println("messageReceived:" + msg);

		// MyMinaEncoder将write的字符串添加了一个小字节序Header并转为字节码
		session.write("收到");
	}
}
