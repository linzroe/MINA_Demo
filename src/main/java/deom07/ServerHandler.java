package deom07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Collection;

import org.apache.mina.core.IoUtil;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter{
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {

		// 获取所有正在连接的IoSession
		Collection<IoSession> sessions = session.getService().getManagedSessions().values();

		// 将消息写到所有IoSession
		IoUtil.broadcast(message, sessions);
	}

}
