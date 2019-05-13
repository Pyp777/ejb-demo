package ejb.session.old;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class MyOldBean implements SessionBean{

	@Override
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		System.out.println("MyOldBean.ejbActivate()");
	}

	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		System.out.println("MyOldBean.ejbPassivate()");
	}

	@Override
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		System.out.println("MyOldBean.ejbRemove()");
	}

	@Override
	public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		System.out.println("MyOldBean.setSessionContext()");
	}
	
	public String getData() {
		return "Data from old bean";
	}
}
