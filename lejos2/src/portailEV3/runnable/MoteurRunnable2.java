package portailEV3.runnable;

import java.sql.Time;
import java.util.Timer;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import portailEV3.enumeration.EtatPorte;
import portailEV3.hardware.CapteurContact;
import portailEV3.hardware.CapteurPresence;
import portailEV3.hardware.Porte;
import portailEV3.log.LogEV3;

public class MoteurRunnable2 implements Runnable {

	private CapteurContact capteurOuvert;
	private CapteurContact capteurFerme;
	private CapteurPresence capteurPresence;
	private Porte portePincipale;
	private SoundRunnable sound;
	private Float positionOuvert;
	private Float positionFerme;
	private Long tempsOuverture;

	private boolean isRunning = true;
	private volatile int action; // 1 = ouvrir, 2 = fermer

	public MoteurRunnable2(CapteurContact capteurOuvert, CapteurContact capteurFerme, Porte porteP, CapteurPresence cp, SoundRunnable sound) {
		this.capteurOuvert = capteurOuvert;
		this.capteurFerme = capteurFerme;
		this.portePincipale = porteP;
		this.capteurPresence = cp;
		this.sound = sound;
	}

	@Override
	public void run() {

		while (true) {
			if (this.isRunning) {
				switch (this.action) {
				case 1:
					ouvrir();
					break;
				case 2:
					fermer();
					break;
				default:
					break;
				}

				this.action = 0;
				this.stopThread();
			}
		}

	}

	public void ouvrir() {
		this.portePincipale.setEtat(EtatPorte.EN_OUVERTURE);
		this.sound.resumeThread();
		long startTime = System.nanoTime();
		while (!this.capteurOuvert.contact() && this.isRunning && !this.capteurPresence.presence()) {
			long endCurrentTime = System.nanoTime();
			if ( this.tempsOuverture != null && (endCurrentTime - startTime > this.tempsOuverture + 2000000000)) {
				this.portePincipale.stop(true);
				LogEV3.addLog("Disfonctionnement capteur ouverture");
				break;
			} else {
				this.portePincipale.ouvrir();
			}
		}
		long endTime = System.nanoTime();
		if (this.tempsOuverture == null) this.tempsOuverture = endTime - startTime;
		LogEV3.addLog("L'ouverture de la porte a pris" + (endTime-startTime) + "secondes.");

		if (this.capteurOuvert.contact()) {
			this.portePincipale.setEtat(EtatPorte.OUVERTE);
		} else {
			this.portePincipale.setEtat(EtatPorte.ARRETEE_EN_OUVERTURE);
		}
		this.sound.stopThread();
		this.portePincipale.stop(true);
		
		if (this.positionOuvert == null) {
			this.positionOuvert = this.portePincipale.getPosition();
		}
	}

	public void fermer() {
		this.portePincipale.setEtat(EtatPorte.EN_FERMETURE);
		this.sound.resumeThread();
		while (!this.capteurFerme.contact() && this.isRunning && !this.capteurPresence.presence()) {
			this.portePincipale.fermer();
		}
		if (this.capteurFerme.contact()) {
			this.portePincipale.setEtat(EtatPorte.FERMEE);
		} else {
			this.portePincipale.setEtat(EtatPorte.ARRETEE_EN_FERMETURE);
		}
		this.sound.stopThread();
		this.portePincipale.stop(true);
		System.out.println("Fermé : " + this.portePincipale.getPosition());
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void stopThread() {
		this.isRunning = false;
	}

	public void resumeThread() {
		this.isRunning = true;
	}

	public EtatPorte getEtatPorte() {
		return this.portePincipale.getEtat();
	}
	
	public boolean getIsRunning() {
		return this.isRunning;
	}

}