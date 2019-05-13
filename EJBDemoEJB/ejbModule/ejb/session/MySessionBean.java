package ejb.session;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Session Bean implementation class SessionBean
 * @remote - the bean is going to be accessed from a remote client: 
 */
@Stateless
@Remote(MySessionBeanInterface.class)
public class MySessionBean {

	private Date last = new Date(); 

	@Resource(lookup = "java:/jmx/myQueue")
	Queue queue;
	
	@Resource(lookup = "java:/jmx/myTopic")
	Topic topic;

	@Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
	ConnectionFactory connectionFactory;

	@Resource(lookup = "java:jboss/mail/Default")
	javax.mail.Session mailSession;
	
    /**
     * Default constructor. 
     */
    public MySessionBean() {
        // TODO Auto-generated constructor stub
    }

    public String getData() {
    	return "The very first response from Session Bean at: " + new SimpleDateFormat("HH:mm:ss").format(last);
    }
    
    @Schedule(second = "*/25", minute = "*", hour = "*", persistent = false)
    public void setTime() {
    	last = new Date();
    }
    
    @Schedule(second = "30", minute = "*", hour = "*", persistent = false)
    public void sendMessages() {
		try {
			System.out.println("-----------send messages--------" + Thread.currentThread().getName());
			
			Connection connection = connectionFactory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(topic);
			
			connection.start();
			
			TextMessage message = session.createTextMessage("Scheduled message at: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			producer.send(message);
			
			// not inside app server
			//connection.stop();
			session.close();

		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
    
    @Schedule(second = "45", minute = "*", hour = "*", persistent = false)
    public void sendMails() {
		try {
			//String mailndi = "java:jboss/mail/Default";
			
			System.out.println("-----------send mails--------" + Thread.currentThread().getName());
			
			javax.mail.Message message = new MimeMessage(mailSession);
			message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("pavel.petr@gist.cz"));
			message.setText("Sending mail from bean");
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
