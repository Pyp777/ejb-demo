package ee.tutor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.tutor.old.MyOldBeanComponent;

/**
 * Servlet implementation class BeanServlet
 */
@WebServlet("/BeanServlet")
public class BeanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BeanServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@EJB
	MySessionBeanInterface sessionBean;

	@EJB
	MyOldBeanComponent oldBean;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BeanServlet.doGet()");
		System.out.println("MySessionBeanInterface: " + sessionBean.getClass().getName());

		PrintWriter out = response.getWriter();
		
		// injected bean
		out.println("injected bean: " + sessionBean.getData());

		// check lookups
		String[] lookups = new String[] { 
				"ejb:EJBDemoEAR/EJBDemoEJB/MySessionBeanInterface",
				"ejb:EJBDemoEAR/EJBDemoEJB/MySessionBeanInterface!ee.tutor.MySessionBeanInterfaceInterface",
				"java:global/EJBDemoEAR/EJBDemoEJB/MySessionBeanInterface!ee.tutor.MySessionBeanInterface", 
				"java:app/EJBDemoEJB/MySessionBeanInterface!ee.tutor.MySessionBeanInterface", 
				"java:module/MySessionBeanInterface!ee.tutor.MySessionBeanInterface", 
				"java:global/EJBDemoEAR/EJBDemoEJB/MySessionBeanInterface", 
				"java:app/EJBDemoEJB/MySessionBeanInterface", 
				"java:module/MySessionBeanInterface", 
				};
		for (String l : lookups) {
			System.out.println("--------lookup: " + l);
			try {
				Object bean = InitialContext.doLookup(l);
				System.out.println("lookuped bean: " + bean.getClass().getName());
			} catch (Exception e) {
				System.out.println("lookuped bean failed....");
			}
		}
		
		out.println("");
		try {
			// lookuped bean			
			MySessionBeanInterface bean = InitialContext.doLookup("java:app/EJBDemoEJB/MySessionBeanInterface");
			out.println("lookuped bean: " + bean.getData());
		} catch (NamingException e) {
			out.write(" error message: " + e.getMessage());
		}
		
		// old bean
		out.println("");
		out.println("injected oldBean: " + oldBean.getData());
	}

}
