package ee.tutor;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class SessionBean
 */
@Stateless
@Local(MySessionBeanInterface.class)
public class MySessionBean  {

	int count;
	
    /**
     * Default constructor. 
     */
    public MySessionBean() {
        // TODO Auto-generated constructor stub
    	System.out.println("MySessionBean.MySessionBean()");
    }

    public String getData() {
    	//incCount();
    	return "The very first response from Session Bean - iteration: " + getCount();
    }
    
    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void incCount() {
    	count++;
    }
    
    
    public int getCount() {
    	return count;
    }
}
