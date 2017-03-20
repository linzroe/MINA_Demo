package deom08;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.http.api.DefaultHttpResponse;
import org.apache.mina.http.api.HttpRequest;
import org.apache.mina.http.api.HttpResponse;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TcpServer {
	public static void main(String[] args) throws Exception{
		
		IoAcceptor acceptor=new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new HttpServerCodec());
		acceptor.setHandler(new HttpServerHandle());
		acceptor.bind(new InetSocketAddress(8080));
		
		
	}
}
	class HttpServerHandle extends IoHandlerAdapter{
		
		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {
			cause.printStackTrace();
		}

		@Override
		public void messageReceived(IoSession session, Object message)
				throws Exception {

			if (message instanceof HttpRequest) {

				// 请求，解码器将请求转换成HttpRequest对象
				HttpRequest request = (HttpRequest) message;

				// 获取请求参数
				String name = request.getParameter("name");
				if(name == null) {
					name = "World";
				}
				name = URLDecoder.decode(name, "UTF-8");

				// 响应HTML
				String responseHtml = "<html><body>Hello, " + name + "</body></html>";
				byte[] responseBytes = responseHtml.getBytes("UTF-8");
				int contentLength = responseBytes.length;

				// 构造HttpResponse对象，HttpResponse只包含响应的status line和header部分
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "text/html; charset=utf-8");
				headers.put("Content-Length", Integer.toString(contentLength));
				HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK, headers);
				//http://tp.mz50.cn/app/index.php?i=7&c=entry&rid=541&id=48043&do=vote&m=tyzm_diamondvote=1&winzoom=1
				// 响应BODY
				IoBuffer responseIoBuffer = IoBuffer.allocate(contentLength);
				responseIoBuffer.put(responseBytes);
		        responseIoBuffer.flip();

		        session.write(response); // 响应的status line和header部分
		        session.write(responseIoBuffer); // 响应body部分
			}
		}
		
	}
