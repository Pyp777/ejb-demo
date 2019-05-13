package ejb.servlet;

import java.io.IOException;

import javax.annotation.Resource;
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

@WebServlet("/QueueServlet")
public class QueueServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Resource(lookup = "java:/jmx/myQueue")
	Queue queue;
	
	@Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
	ConnectionFactory connectionFactory;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String text = req.getParameter("text") != null ? req.getParameter("text") : "Hello New World";

		handleMessage(res, text);
	}

	private void handleMessage(HttpServletResponse res, String text) throws IOException {
		try {
			System.out.println("MessageServlet.handleNewStyle() queue: " + queue);
			Connection connection = connectionFactory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(queue);

			connection.start();

			TextMessage message = session.createTextMessage(text);
			publisher.send(message);

			// not inside app server
			//connection.stop();
			session.close();
			
			res.getWriter().println("Message sent: " + text);

		} catch (JMSException e) {
			res.getWriter().println("Error while trying to send <" + text + "> message: " + e.getMessage());
		}
	}	
}
