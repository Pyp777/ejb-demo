package ejb.messages;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: MyTopicMessageBean
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "myTopic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }
)
public class MyTopicMessageBean2 implements MessageListener {

	/**
	 * Default constructor.
	 */
	public MyTopicMessageBean2() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		if (message == null) {
			System.out.println("MessageBean2 - Message is null");
			return;
		}
		if (message instanceof TextMessage) {
			TextMessage text = (TextMessage) message;
			try {
				System.out.println("MessageBean2 - Text message: " + text.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
