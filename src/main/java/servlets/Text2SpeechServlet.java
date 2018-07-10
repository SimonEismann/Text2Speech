package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.RandomStringUtils;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.MaryAudioUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/text2speech")
public class Text2SpeechServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;
	private static javax.ws.rs.client.Invocation.Builder builder;
	private static LocalMaryInterface mary;
	private static Jedis jedis;
	
	public Text2SpeechServlet() {
		builder = ClientBuilder.newClient().target("http://" + System.getenv("FileServerIp"))
				.path("FileServer/rest/save").request(MediaType.TEXT_PLAIN);
		try {
			mary = new LocalMaryInterface();
		} catch (MaryConfigurationException e) {
			throw new IllegalStateException("Could not initialize MaryTTS interface: " + e.getMessage());
		}
		jedis = new Jedis(System.getenv("RedisIp"), 6379, 5000);
	}
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("text");
		double[] audio = text2speech(content);

		
		Entity<DoubleArray> entity = Entity.entity(new DoubleArray(audio), MediaType.APPLICATION_JSON);
		Response r = builder.post(entity);
		String location = r.readEntity(String.class);
		r.close();
		
		String id = RandomStringUtils.randomAlphanumeric(8);

		jedis.set(id, location);
		
		PrintWriter writer = resp.getWriter();
		writer.println("<a href=\"display?id=" + id + "\">Play audio!</a>");
		writer.close();
	}

	public double[] text2speech(String text) {
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

		public DoubleArray() {
		}

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