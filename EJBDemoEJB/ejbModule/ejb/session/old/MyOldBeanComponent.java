package ejb.session.old;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface MyOldBeanComponent extends EJBObject {
	public String getData() throws RemoteException;
}
