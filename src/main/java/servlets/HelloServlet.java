package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/*")
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Java Beispiel Servlet</title></head>");
        writer.println("<body>");
        writer.println("<h1>Servlet Beispiel</h1>");
        writer.println("<p>Inhalt der HTML-Seite</p>");
        writer.println("<body>");
        writer.println("</html>");

        writer.close();
    }
}