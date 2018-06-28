package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/redis")
public class RedisServlet extends HttpServlet {

	private static final long serialVersionUID = 3725150619150580957L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Jedis jedis = new Jedis(System.getenv("RedisIp"));
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		jedis.close();
		
		PrintWriter writer = resp.getWriter();
		writer.println(value);

		writer.close();
	}
}