package portailEV3.test;

import static org.junit.Assert.*;

import org.junit.Test;

import portailEV3.enumeration.EtatPortail;
import portailEV3.enumeration.EtatPorte;
import portailEV3.hardware.Porte;
import portailEV3.runnable.MoteurRunnable;
import portailEV3.runnable.PortailRunnable;

public class PortailRunnableTest {

	PortailRunnable portailRunnable;
	
	@Test
	public void getEtatPortailTest() {
		Porte porteGauche = new Porte();
		Porte porteDroite = new Porte();
		MoteurRunnable moteurGauche = new MoteurRunnable(null, null, porteGauche, null, null, null);
		MoteurRunnable moteurDroit = new MoteurRunnable(null, null, porteDroite, null, null, null);
		portailRunnable = new PortailRunnable(moteurDroit, moteurGauche);
		
		assertTrue(portailRunnable != null);
		
		porteGauche.setEtat(EtatPorte.FERMEE);
		porteDroite.setEtat(EtatPorte.FERMEE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.FERME);
		
		porteGauche.setEtat(EtatPorte.OUVERTE);
		porteDroite.setEtat(EtatPorte.OUVERTE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.OUVERT);
		
		porteGauche.setEtat(EtatPorte.OUVERTE);
		porteDroite.setEtat(EtatPorte.FERMEE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.OUVERT_PARTIELLEMENT);
		
		porteGauche.setEtat(EtatPorte.EN_OUVERTURE);
		porteDroite.setEtat(EtatPorte.EN_OUVERTURE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.EN_OUVERTURE_TOTALE);
		
		porteGauche.setEtat(EtatPorte.EN_OUVERTURE);
		porteDroite.setEtat(EtatPorte.FERMEE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.EN_OUVERTURE_PARTIELLE);
		
		porteGauche.setEtat(EtatPorte.EN_FERMETURE);
		porteDroite.setEtat(EtatPorte.EN_FERMETURE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.EN_FERMETURE_TOTALE);
		
		porteGauche.setEtat(EtatPorte.EN_OUVERTURE);
		porteDroite.setEtat(EtatPorte.OUVERTE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.EN_OUVERTURE_TOTALE);
		
		porteGauche.setEtat(EtatPorte.OUVERTE);
		porteDroite.setEtat(EtatPorte.EN_OUVERTURE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.EN_OUVERTURE_TOTALE);
		
		porteGauche.setEtat(EtatPorte.ARRETEE_EN_OUVERTURE);
		porteDroite.setEtat(EtatPorte.ARRETEE_EN_OUVERTURE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.ARRETE_EN_OUVERTURE);
		
		porteGauche.setEtat(EtatPorte.ARRETEE_EN_FERMETURE);
		porteDroite.setEtat(EtatPorte.ARRETEE_EN_FERMETURE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.ARRETE_EN_FERMETURE);
		
		porteGauche.setEtat(EtatPorte.ARRETEE_EN_OUVERTURE);
		porteDroite.setEtat(EtatPorte.FERMEE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.OUVERT_PARTIELLEMENT);
		
		porteGauche.setEtat(EtatPorte.ARRETEE_EN_FERMETURE);
		porteDroite.setEtat(EtatPorte.FERMEE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.ARRETE_EN_FERMETURE);
		
		porteGauche.setEtat(EtatPorte.EN_FERMETURE);
		porteDroite.setEtat(EtatPorte.FERMEE);
		assertTrue(portailRunnable.getEtatPortail() == EtatPortail.EN_FERMETURE_PARTIELLE);
		
	}
	
	

}
