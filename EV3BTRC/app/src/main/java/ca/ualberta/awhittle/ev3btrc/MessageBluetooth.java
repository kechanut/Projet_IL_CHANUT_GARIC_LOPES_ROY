package ca.ualberta.awhittle.ev3btrc;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageBluetooth implements Serializable {

    private static final long serialVersionUID = 5420167516704495496L;

    private String commande;
    private ArrayList<String> parametres;

    public MessageBluetooth(String commande, ArrayList<String> parametres) {
        this.commande = commande;
        this.parametres = parametres;
    }

    public String getCommande() {
        return this.commande;
    }

    public ArrayList<String> getParams() {
        return this.parametres;
    }
}