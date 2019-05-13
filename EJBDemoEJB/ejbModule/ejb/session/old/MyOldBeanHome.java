package ejb.session.old;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MyOldBeanHome extends EJBHome {
	public MyOldBeanComponent create() throws CreateException, RemoteException;
}
