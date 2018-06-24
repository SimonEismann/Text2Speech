package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/text2speech")
public class Text2SpeechServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("text");
		// init mary
		PrintWriter writer = resp.getWriter();
//		WebTarget target = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp")).path("FileServer/rest/hello");
//		String itSays = target.request(MediaType.TEXT_PLAIN).get(String.class);
		 
		writer.println("<html>");
		writer.println("<head><title>Text2Speech Upload</title></head>");
		writer.println("<body>");
		writer.println("<p>" + content + "</p>");
		writer.println("<body>");
		writer.println("</html>");

		writer.close();
	}
	
	public AudioInputStream text2speech(String text) {
		LocalMaryInterface mary = null;
		try {
			mary = new LocalMaryInterface();
		} catch (MaryConfigurationException e) {
			throw new IllegalStateException("Could not initialize MaryTTS interface: " + e.getMessage());
		}

		// synthesize
		AudioInputStream audio = null;
		try {
			audio = mary.generateAudio(text);
		} catch (SynthesisException e) {
			throw new IllegalStateException("Synthesis failed: " + e.getMessage());
		}
		return audio;
	}
}