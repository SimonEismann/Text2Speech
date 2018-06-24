package servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;

import org.apache.commons.io.IOUtils;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/text2speech")
public class Text2SpeechServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("text");
		AudioInputStream audio = text2speech(content);
		
		resp.setContentType("audio/x-wav");
		resp.setHeader("Accept-Ranges", "bytes");
		IOUtils.copy(audio, resp.getOutputStream());
		
//		WebTarget target = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp")).path("FileServer/rest/hello");
//		String itSays = target.request(MediaType.TEXT_PLAIN).get(String.class);
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