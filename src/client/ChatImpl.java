package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commun.Chat;


/** 
 *  Implémentation de l'interface Chat
 *
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */
public class ChatImpl extends UnicastRemoteObject implements Chat {

    public String name;
 
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