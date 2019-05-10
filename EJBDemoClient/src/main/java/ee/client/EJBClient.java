package ee.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class EJBClient {

	public static void main(String[] args) throws Exception {
		Properties jndiProps = new Properties();
		jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		// credentials
		//jndiProps.put(Context.SECURITY_PRINCIPAL, "peter");
		//jndiProps.put(Context.SECURITY_CREDENTIALS, "lois");
		
		// This is an important property to set if you want to do EJB invocations via
		// the remote-naming project
		jndiProps.put("jboss.naming.client.ejb.context", true);
		// create a context passing these properties
		Context ctx = new InitialContext(jndiProps);
		// lookup the bean Foo

		Object bean = null; 
		//bean = ctx.lookup("java:app/EJBDemoBeans/MySessionBean");
		bean = ctx.lookup("EJBDemoBeans/MySessionBean");
		System.out.println("lookuped bean: " + bean);
	}
}
