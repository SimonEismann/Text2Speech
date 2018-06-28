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
		
		writer.println("<html>");
		writer.println("<head><title>Text2Speech Upload</title></head>");
		writer.println("<body>");
		writer.println("<h1>Text2Speech Upload</h1>");
		writer.println("<form action=\"text2speech\" method=\"post\">");
		writer.println("<textarea id=\"text\" name=\"text\" rows=\"4\" cols=\"50\">Hello World!</textarea><br /><br />");
		writer.println("<input type=\"submit\" value=\"Submit\">");
		writer.println("</form>");
		writer.println("<body>");
		writer.println("</html>");

		writer.close();
	}
}