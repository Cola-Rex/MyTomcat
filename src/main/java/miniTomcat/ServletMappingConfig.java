package miniTomcat;

import java.util.ArrayList;
import java.util.List;

public class ServletMappingConfig {

	public static List<ServletMapping> servletMappingList = new ArrayList<>();
	
	static {
		servletMappingList.add(new ServletMapping("findGirl", "/girl", "kc.servlet.FindGirlServlet"));
		servletMappingList.add(new ServletMapping("helloWorld", "/world", "kc.servlet.HelloWorldServlet"));
	}
}
