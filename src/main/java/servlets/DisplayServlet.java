package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import redis.clients.jedis.Jedis;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

@WebServlet("/display")
public class DisplayServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");

		Jedis jedis = new Jedis(System.getenv("RedisIp"), 6379, 5000);
		String location = jedis.get(id);
		jedis.close();
		
		BufferedInputStream audioStream = new BufferedInputStream(new URL(location).openStream());

		resp.setContentType("audio/x-wav");
		resp.setHeader("Accept-Ranges", "bytes");
		IOUtils.copy(audioStream, resp.getOutputStream());
	}
}