package ee.tutor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class SessionBean
 */
@Stateless
@LocalBean
public class MySessionBean {

    /**
     * Default constructor. 
     */
    public MySessionBean() {
        // TODO Auto-generated constructor stub
    }

    public String getData() {
    	return "The very first response from Session Bean";
    }
}
