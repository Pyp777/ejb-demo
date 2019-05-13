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
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * Session Bean implementation class SessionBean
 * @remote - the bean is going to be accessed from a remote client: 
 */
@Stateless
@Remote(MySessionBeanInterface.class)
public class MySessionBean {

	private Date last = new Date(); 

	@Resource(lookup = "java:/jmx/myTopic")
	Topic topic;

	@Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
	ConnectionFactory connectionFactory;
	
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
    
}
