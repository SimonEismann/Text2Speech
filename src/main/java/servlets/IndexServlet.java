package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
//		WebTarget target = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp")).path("FileServer/rest/hello");
//		String itSays = target.request(MediaType.TEXT_PLAIN).get(String.class);
		 
		writer.println("<html>");
		writer.println("<head><title>Text2Speech Upload</title></head>");
		writer.println("<body>");
		writer.println("<h1>Text2Speech Upload</h1>");
		writer.println("<form action=\"text2speech\" method=\"post\">");
		writer.println("<textarea id=\"text\" name=\"text\" rows=\"4\" cols=\"50\">Hello World!</textarea>");
		writer.println("<input type=\"submit\" value=\"Submit\"><br />");
		writer.println("</form>");
		writer.println("<body>");
		writer.println("</html>");

		writer.close();
	}
}