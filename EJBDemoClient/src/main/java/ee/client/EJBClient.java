package ee.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ee.tutor.MySessionBean;

//import ee.tutor.MySessionBean;

public class EJBClient {

	/**
	 * for client is right jndi ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean
	 * 
	 * https://docs.jboss.org/author/display/AS71/EJB+invocations+from+a+remote+client+using+JNDI
	 * 
	 * For stateless beans:
	 * ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>
	 * 
	 * For stateful beans:
	 * ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>?stateful
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//lookupTest();
		
		lookupBean();
	}

	/**
	 * direct lookup
	 * @throws NamingException
	 */
	private static void lookupBean() throws NamingException {
		Properties jndiProps = getEnv("localhost:8080");

		Context ctx = new InitialContext(jndiProps);

		Object beanObject = ctx.lookup("ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBeanInterface");// !ee.tutor.MySessionBean
		System.out.println("lookuped bean: " + beanObject);	
		
		ee.tutor.MySessionBeanInterface bean = (ee.tutor.MySessionBeanInterface)beanObject; 
		System.out.println("lookuped bean: " + bean.getData());
	}
	
	/**
	 * check lookup names
	 * @throws NamingException
	 */
	private static void lookupTest() throws NamingException {
		Properties jndiProps = getEnv("localhost:8080");

		Context ctx = new InitialContext(jndiProps);

		Object bean = null;

		// check lookups
		String[] lookups = new String[] { "ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean",
				"ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean",
				"java:global/EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean",
				"java:app/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean",
				"java:module/MySessionBean!ee.tutor.MySessionBean",
				"ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean",
				"java:global/EJBDemoEAR/EJBDemoEJB/MySessionBean", "java:app/EJBDemoEJB/MySessionBean",
				"java:module/MySessionBean", };
		for (String l : lookups) {
			System.out.println("--------lookup: " + l);
			try {
				bean = ctx.lookup(l);
				System.out.println("lookuped bean: " + bean.getClass().getName());
			} catch (Exception e) {
				System.out.println("lookuped bean failed....");
			}
		}
	}

	public static Properties getEnv(final String server) {

		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		env.put("jboss.naming.client.ejb.context", true);
		env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
		// credentials
		env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "true");
		env.put(Context.SECURITY_PRINCIPAL, "admin");
		env.put(Context.SECURITY_CREDENTIALS, "admin");

		env.put(Context.PROVIDER_URL, "http-remoting://" + server);

		return env;
	}
}
