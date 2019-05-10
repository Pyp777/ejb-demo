package ee.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class EJBClient {

	/*
	java:global/EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean
	java:app/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean
	java:module/MySessionBean!ee.tutor.MySessionBean
	ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean
	java:global/EJBDemoEAR/EJBDemoEJB/MySessionBean
	java:app/EJBDemoEJB/MySessionBean
	java:module/MySessionBean
	*/
	public static void main(String[] args) throws Exception {
		Properties jndiProps = getEnv("localhost:8080/EJBDemoWEB/");

		Context ctx = new InitialContext(jndiProps);

		Object bean = null;

		// check lookups
		String[] lookups = new String[] { 
				"java:global/EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean", 
				"java:app/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean", 
				"java:module/MySessionBean!ee.tutor.MySessionBean", 
				"ejb:EJBDemoEAR/EJBDemoEJB/MySessionBean!ee.tutor.MySessionBean", 
				"java:global/EJBDemoEAR/EJBDemoEJB/MySessionBean", 
				"java:app/EJBDemoEJB/MySessionBean", 
				"java:module/MySessionBean", 
				};
		for (String l : lookups) {
			System.out.println("--------lookup: " + l);
			try {
				bean = ctx.lookup(l);
				System.out.println("lookuped bean: " + bean.getClass().getName());
			} catch (Exception e) {
				System.out.println("lookuped bean failed....");
			}
		}

		System.out.println("lookuped bean: " + bean);
	}

	public static Properties getEnv(final String server) {

		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		env.put("jboss.naming.client.ejb.context", true);
		env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
		env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

		// credentials
		env.put(Context.SECURITY_PRINCIPAL, "admin");
		env.put(Context.SECURITY_CREDENTIALS, "admin");
		
		env.put(Context.PROVIDER_URL, "http-remoting://" + server);

		return env;
	}
}
