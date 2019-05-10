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

import ee.tutor.old.MyOldBean;
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
	MySessionBean sessionBean;

	@EJB
	MyOldBeanComponent oldBean;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BeanServlet.doGet()");
		System.out.println("MySessionBean: " + sessionBean.getClass().getName());

		PrintWriter out = response.getWriter();
		
		// injected bean
		out.println("injected bean: " + sessionBean.getData());

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
				Object bean = InitialContext.doLookup(l);
				System.out.println("lookuped bean: " + bean.getClass().getName());
			} catch (Exception e) {
				System.out.println("lookuped bean failed....");
			}
		}
		
		out.println("");
		try {
			// lookuped bean			
			MySessionBean bean = InitialContext.doLookup("java:app/EJBDemoEJB/MySessionBean");
			out.println("lookuped bean: " + bean.getData());
		} catch (NamingException e) {
			out.write(" error message: " + e.getMessage());
		}
		
		// old bean
		out.println("");
		out.println("injected oldBean: " + oldBean.getData());
	}

}
