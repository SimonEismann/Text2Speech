package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.MaryAudioUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

@WebServlet("/text2speech")
public class Text2SpeechServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("text");
		double[] audio = text2speech(content);
		
		WebTarget target = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp")).path("FileServer/rest/save");
		Entity<DoubleArray> entity = Entity.entity(new DoubleArray(audio), MediaType.APPLICATION_JSON);
		String itSays = target.request(MediaType.TEXT_PLAIN).post(entity, String.class);
				
		System.out.println(itSays);
		BufferedInputStream audioStream = new BufferedInputStream(new URL(itSays).openStream());

		IOUtils.copy(audioStream, resp.getOutputStream());
	}
	
	public double[] text2speech(String text) {
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
		
		return MaryAudioUtils.getSamplesAsDoubleArray(audio);
	}
	
	public static class DoubleArray {
	    private double[] array;

	    public DoubleArray() { }

	    public DoubleArray(double[] array) {
	        this.array = array;
	    }

	    public void setArray(double[] array) {
	        this.array = array;
	    }

	    public double[] getArray() {
	        return this.array;
	    }
	}
}