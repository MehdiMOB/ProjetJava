package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commun.Chat;


/* la classe ChatImpl impl�mente l'interface de l'objet distant
    Cette classe impl�mente les m�thodes getName, getClient, setClient et sendMessage
*/ 
public class ChatImpl extends UnicastRemoteObject implements Chat {

    public String name;
    //public ChatInterface client = null;
 
    public ChatImpl(String n)  throws RemoteException { 
        this.name = n;
    }
    
    public String getName() throws RemoteException {
        return this.name;
    }
 
    public void sendMessage(String msg) throws RemoteException {
        System.out.println(msg);
    }
}