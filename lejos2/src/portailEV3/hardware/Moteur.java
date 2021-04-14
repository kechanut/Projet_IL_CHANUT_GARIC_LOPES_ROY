package portailEV3.hardware;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;

public class Moteur {

	EV3LargeRegulatedMotor moteur;

	Moteur(Port port) {
		this.moteur = new EV3LargeRegulatedMotor(port);
	}

	void pousser() {
		this.moteur.setSpeed(30);
		this.moteur.forward();
		//this.moteur.rotate(5);
	}

	void tirer() {
		this.moteur.setSpeed(30);
		this.moteur.backward();
		//this.moteur.rotate(-5);
	}

	void stop() {
		this.moteur.stop();
	}
	
	public float getPosition() {
		return this.moteur.getPosition();
	}
}
