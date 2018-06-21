package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/*")
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();

		System.out.println();
		
		WebTarget target = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp")).path("FileServer/rest/hello");
		String itSays = target.getUri().toString();
//		String itSays = target.request(MediaType.TEXT_PLAIN).get(String.class);
		 
		writer.println("<html>");
		writer.println("<head><title>Java Beispiel Servlet</title></head>");
		writer.println("<body>");
		writer.println("<h1>Servlet Beispiel</h1>");
		writer.println("<p>The file server says: " + itSays + "</p>");
		writer.println("<body>");
		writer.println("</html>");

		writer.close();
	}
}