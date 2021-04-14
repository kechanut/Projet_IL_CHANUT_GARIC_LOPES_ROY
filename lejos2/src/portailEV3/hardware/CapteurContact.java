package portailEV3.hardware;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class CapteurContact {

	EV3TouchSensor capteurContact;

	public CapteurContact(Port port) {
		this.capteurContact = new EV3TouchSensor(port);
	}

	public boolean contact() {

		float[] sample = new float[capteurContact.sampleSize()];
		capteurContact.fetchSample(sample, 0);

		float etat = sample[0];
		
		if (etat == 1)
			return true;
		else
			return false;

	}

}
