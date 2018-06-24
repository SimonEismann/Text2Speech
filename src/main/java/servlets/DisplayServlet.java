package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/display")
public class DisplayServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
//		WebTarget target = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp")).path("FileServer/rest/hello");
//		String itSays = target.request(MediaType.TEXT_PLAIN).get(String.class);
		writer.println("<html class=\"gr__text2speech_default_10_1_232_110_xip_io\"><head><meta name=\"viewport\" content=\"width=device-width\"></head><body data-gr-c-s-loaded=\"true\"><video controls=\"\" autoplay=\"\" name=\"media\"><source src=\"http://text2speech.default.10.1.232.110.xip.io/Text2Speech/text2speech?text=hello\" type=\"audio/x-wav\"></video></body></html>");

		writer.close();
	}
}