package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{

	public String getName() throws RemoteException;
	public void sendMessage(String msg) throws RemoteException;
	
}
