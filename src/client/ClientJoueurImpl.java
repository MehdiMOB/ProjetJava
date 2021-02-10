package client;

import java.rmi.server.UnicastRemoteObject;

import commun.Client_joueur;
import jeu.Equipe;

public class ClientJoueurImpl extends UnicastRemoteObject implements Client_joueur {

	Equipe equipeAdverse;
	public ClientJoueurImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notification(String message) {
		// TODO Auto-generated method stub

	}

	@Override	
	public void equipeAdverse(Equipe equipe_adverse) {
		// TODO Auto-generated method stub
		equipeAdverse = equipe_adverse;
		this.notify();
	}
	
	public Equipe getEquipeAdverse() {
		return equipeAdverse;
	}

}
