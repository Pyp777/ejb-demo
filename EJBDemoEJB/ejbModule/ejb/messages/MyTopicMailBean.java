package ejb.messages;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message-Driven Bean implementation class for: MyTopicMessageBean
 */
@MessageDriven(activationConfig = { 
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "myTopic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class MyTopicMailBean implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		String mailndi = "java:jboss/mail/Default";
		
		
		
	}

}
