package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.junit.Test;

import uilt.LittleEndian;

public class test04 {

	
		@Test
		public void aaaa()throws Exception{
			
			Socket socket = null;
			OutputStream out = null;
			InputStream in = null;

			try {

				socket = new Socket("localhost", 5454);
				out = socket.getOutputStream();
				in = socket.getInputStream();

				// 请求服务器
				String data = "客户";
				byte[] outputBytes = data.getBytes("UTF-8");
				out.write(LittleEndian.toLittleEndian(outputBytes.length)); // write header
				out.write(outputBytes); // write body
				out.flush();

				// 获取响应
				byte[] inputBytes = new byte[1024];
				int length = in.read(inputBytes);
				if(length >= 4) {
					int bodyLength = LittleEndian.getLittleEndianInt(inputBytes);
					if(length >= 4 + bodyLength) {
						byte[] bodyBytes = Arrays.copyOfRange(inputBytes, 4, 4 + bodyLength);
						System.out.println("Header:" + bodyLength);
						System.out.println("Body:" + new String(bodyBytes, "UTf-8"));
					}
				}

			} finally {
				// 关闭连接
				in.close();
				out.close();
				socket.close();
			}
			
		}
	
}
