package portailEV3.hardware;

import lejos.hardware.port.Port;
import portailEV3.enumeration.EtatPorte;

public class Porte {

	Moteur moteur;
	EtatPorte etat;

	public Porte(Port port) {
		moteur = new Moteur(port);
		etat = EtatPorte.INCONNU;
	}

	public void ouvrir() {

		this.moteur.pousser();
		this.etat = EtatPorte.EN_OUVERTURE;

	}
	
	public void fermer() {

		this.moteur.tirer();
		this.etat = EtatPorte.EN_FERMETURE;

	}
	
	public void stop(boolean ouvert) {
		this.moteur.stop();
	}
	
	public EtatPorte getEtat() {
		return this.etat;
	}
	
	public void setEtat(EtatPorte etatPorte) {
		this.etat = etatPorte;
	}
	
	public float getPosition() {
		return this.moteur.getPosition();
	}
	
}
