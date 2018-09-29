package bioTomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	@SuppressWarnings("resource")
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(8848);
		Socket socket = null;
		System.out.println("你开始了？");
		while(true) {
			socket = serverSocket.accept();
			Thread thread = new Thread(new HttpServerThread(socket));
			thread.start();
		}
	}
	
	//内部类
	private class HttpServerThread implements Runnable {
		Socket socket = null;
		
		public HttpServerThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			InputStream is = null;
			OutputStream os = null;
			BufferedReader br = null;
			try {
				is = this.socket.getInputStream();
				os = this.socket.getOutputStream();
				
				//页面请求
				br = new BufferedReader(new InputStreamReader(is));
				String line = br.readLine();
				int i = 0;
				while (line != null && br.ready()) {
					line = br.readLine();
					System.out.println("第" + i + "行信息：" + line);
					i++;
				}
				
				//页面响应
				String reply = "HTTP/1.1\n"; //添加响应头
				reply += "Content-Type: text/html\n\r"; //添加响应头
				reply += "服务器返回的消息";
				os.write(reply.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					os.close();
					is.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new Server().start();
	}
}
