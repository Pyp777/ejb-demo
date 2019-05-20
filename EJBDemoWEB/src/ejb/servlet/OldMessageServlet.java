package ejb.servlet;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OldMessageServlet")
public class OldMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String text = req.getParameter("text") != null ? req.getParameter("text") : "Hello Old World";

		handleMessage(res, text);
	}

	private void handleMessage(HttpServletResponse res, String text) throws IOException {
		try {
			Context ic = new InitialContext();

			// java:/myJmsTest/MyConnectionFactory
			ConnectionFactory cf = (ConnectionFactory) ic.lookup("java:comp/DefaultJMSConnectionFactory");
			Queue queue = (Queue) ic.lookup("java:/jmx/myQueue");

			Connection connection = cf.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(queue);

			connection.start();

			TextMessage message = session.createTextMessage(text);
			publisher.send(message);

			// not inside app server
			//connection.stop();
			session.close();

			res.getWriter().println("Message sent: " + text);

		} catch (NamingException | JMSException e) {
			res.getWriter().println("Error while trying to send <" + text + "> message: " + e.getMessage());
		}
	}
}
