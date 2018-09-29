package miniTomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import miniTomcat.servlet.MyServlet;

public class MyTomcat {

	private int port;
	
	private Map<String, String> urlServletMap = new HashMap<>();
	
	public MyTomcat(int port) {
		this.port = port;
	}
	
	public void start() {
		initServletMapping();
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...麦克做得启动！");
			
			//while(true) {
				Socket socket = serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				
				MyRequest myRequest = new MyRequest(inputStream);
				MyResponse myResponse = new MyResponse(outputStream);
				
				//分发请求
				dispatch(myRequest, myResponse);
				
				socket.close();
			//}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void initServletMapping() {
		for(ServletMapping servletMapping : ServletMappingConfig.servletMappingList) {
			urlServletMap.put(servletMapping.getUrl(),  servletMapping.getClazz());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void dispatch(MyRequest myRequest, MyResponse myResponse) {
		
		String clazz = urlServletMap.get(myRequest.getUrl());
		//反射
		try {
			if (clazz != null && !clazz.equals("")) {
				Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(clazz);
				MyServlet myServlet = myServletClass.newInstance();
				
				myServlet.service(myRequest, myResponse);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MyTomcat(8080).start();
	}
}
