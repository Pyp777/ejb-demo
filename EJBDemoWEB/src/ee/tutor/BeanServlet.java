package ee.tutor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BeanServlet.doGet()");
		
		PrintWriter out = response.getWriter();
		out.println("sessionBeanData: " + sessionBean.getData());
	}

}
