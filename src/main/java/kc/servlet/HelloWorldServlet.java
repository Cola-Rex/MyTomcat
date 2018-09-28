package kc.servlet;

import kc.MyRequest;
import kc.MyResponse;

public class HelloWorldServlet extends MyServlet{

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("get world~~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("post world~~~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
