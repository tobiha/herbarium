package at.thammerer.herbarium.spring;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Controller
public class JsonRpcController {
//	@Autowired
//	@Qualifier("bsmTxServiceExport")
//	JsonServiceExporter bsmTxServiceExport;
//
//
//	@RequestMapping(value = "/json/txService", method = RequestMethod.POST)
//	public void bsmTxServicePost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		bsmTxServiceExport.handleRequest(request, response);
//	}


}
