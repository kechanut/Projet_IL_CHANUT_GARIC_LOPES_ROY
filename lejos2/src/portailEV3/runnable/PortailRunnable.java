package portailEV3.runnable;

import java.io.File;

import portailEV3.enumeration.EtatPortail;
import portailEV3.enumeration.EtatPorte;
import portailEV3.log.LogEV3;

public class PortailRunnable implements Runnable {
	
	private MoteurRunnable moteurDroit;
	private MoteurRunnable moteurGauche;
	private EtatPortail etatPortail;
	
	public PortailRunnable(MoteurRunnable md, MoteurRunnable mg) {
		this.moteurDroit = md;
		this.moteurGauche = mg;
	}

	@Override
	public void run() {
		
		this.etatPortail = EtatPortail.INCONNU;
		EtatPortail etatPortailPrec = EtatPortail.INCONNU;
		
		while (true) {
			
			// Changement d'état du portail
			majEtatPortail();
			if (this.etatPortail != etatPortailPrec && this.etatPortail != EtatPortail.INCONNU ) {
				//System.out.println(etatPorteGauche + " " + etatPorteDroite);
				LogEV3.addLog(etatPortail.toString());
			}
			etatPortailPrec = etatPortail;
		}
	}
	
	public EtatPortail EtatPortail() {
		return this.etatPortail;
	}
	
	private EtatPortail getEtatPortail(EtatPorte etatPorteGauche, EtatPorte etatPorteDroite) {
		EtatPortail etatDuPortail;
		if ( EtatPorte.FERMEE == etatPorteGauche && EtatPorte.FERMEE == etatPorteDroite) {
			etatDuPortail = EtatPortail.FERME;
		} else if ( EtatPorte.OUVERTE == etatPorteGauche && EtatPorte.OUVERTE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.OUVERT;
		} else if ( EtatPorte.OUVERTE == etatPorteGauche && EtatPorte.FERMEE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.OUVERT_PARTIELLEMENT;
		} else if ( EtatPorte.EN_OUVERTURE == etatPorteGauche && EtatPorte.EN_OUVERTURE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.EN_OUVERTURE_TOTALE;
		} else if ( EtatPorte.EN_OUVERTURE == etatPorteGauche && EtatPorte.FERMEE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.EN_OUVERTURE_PARTIELLE;
		} else if ( EtatPorte.EN_FERMETURE == etatPorteGauche && EtatPorte.EN_FERMETURE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.EN_FERMETURE_TOTALE;
		} else if ( EtatPorte.EN_OUVERTURE == etatPorteGauche && EtatPorte.OUVERTE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.EN_OUVERTURE_TOTALE;
		} else if ( EtatPorte.OUVERTE == etatPorteGauche && EtatPorte.EN_OUVERTURE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.EN_OUVERTURE_TOTALE;
		} else if ( EtatPorte.ARRETEE_EN_OUVERTURE == etatPorteGauche && EtatPorte.ARRETEE_EN_OUVERTURE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.ARRETE_EN_OUVERTURE;
		} else if ( EtatPorte.ARRETEE_EN_FERMETURE == etatPorteGauche && EtatPorte.ARRETEE_EN_FERMETURE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.ARRETE_EN_FERMETURE;
		} else if ( EtatPorte.ARRETEE_EN_OUVERTURE == etatPorteGauche && EtatPorte.FERMEE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.OUVERT_PARTIELLEMENT;
		} else if ( EtatPorte.ARRETEE_EN_FERMETURE == etatPorteGauche && EtatPorte.FERMEE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.ARRETE_EN_FERMETURE;
		} else if ( EtatPorte.EN_FERMETURE == etatPorteGauche && EtatPorte.FERMEE == etatPorteDroite ) {
			etatDuPortail = EtatPortail.EN_FERMETURE_PARTIELLE;
		} else {
			etatDuPortail = EtatPortail.INCONNU;
		}
		return etatDuPortail;
	}
	
	public EtatPortail getEtatPortail() {
		majEtatPortail();
		return this.etatPortail;
	}
	
	public void majEtatPortail() {
		this.etatPortail = getEtatPortail(moteurGauche.getEtatPorte(), moteurDroit.getEtatPorte());
	}
}