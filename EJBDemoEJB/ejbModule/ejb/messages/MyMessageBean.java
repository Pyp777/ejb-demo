package ejb.messages;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: MyMessage
 * there must be created queue/myQueue under wildfly 16
 * 
 * in menu Messaging/Server/default/Destinations/JMS Queue
 * add
 * 	 name = myQueue
 *   entries = queue/myQueue (enter)
 */
@MessageDriven(activationConfig = { 
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "myQueue"),
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") 
		})
public class MyMessageBean implements MessageListener {

	/**
	 * Default constructor.
	 */
	public MyMessageBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		if (message == null) {
			System.out.println("Message is null");
			return;
		}
		if (message instanceof TextMessage) {
			TextMessage text = (TextMessage) message;
			try {
				System.out.println("Text message: " + text.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
