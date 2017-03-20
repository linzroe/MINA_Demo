package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Test;

public class test02 {
	/**
	 * 测试发送分别发送消息
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args)throws IOException {
		Socket  socket=null;
		OutputStream out=null;
			try {
					socket=new Socket("127.0.0.1", 4515);
					out=socket.getOutputStream();
					String str = "床前明月光\r\n疑是地上霜\r\n举头望明月\r\n低头思故乡\r\n";
					byte[] outputBytes=str.getBytes("UTF-8");
					out.write(outputBytes);
					out.flush();
			} catch (Exception e) {

			}finally {
				out.close();
				socket.close();
			}
	}

	@Test
	public void test11() throws Exception{
		Socket socket = null;
		OutputStream out = null;

		try{

			socket = new Socket("127.0.0.1", 4515);  
			out = socket.getOutputStream();

			String lines = "床前";
			byte[] outputBytes = lines.getBytes("UTF-8");
			out.write(outputBytes);
			out.flush();

			Thread.sleep(1000);

			lines = "明月";
			outputBytes = lines.getBytes("UTF-8");
			out.write(outputBytes);
			out.flush();

			Thread.sleep(1000);

			lines = "光\r\n疑是地上霜\r\n举头";
			outputBytes = lines.getBytes("UTF-8");
			out.write(outputBytes);
			out.flush();

			Thread.sleep(1000);

			lines = "望明月\r\n低头思故乡\r\n";
			outputBytes = lines.getBytes("UTF-8");
			out.write(outputBytes);
			out.flush();

		} finally {
			// 关闭连接
			out.close();
			socket.close();
		}
	}
	
}
