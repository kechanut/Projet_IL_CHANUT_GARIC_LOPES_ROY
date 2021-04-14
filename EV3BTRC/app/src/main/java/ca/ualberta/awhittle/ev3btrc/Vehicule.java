package ca.ualberta.awhittle.ev3btrc;

public class Vehicule {

    public String nameVehi;
    public String addMac;

    public Vehicule(String nameVehi, String addMac) {
        this.nameVehi = nameVehi;
        this.addMac = addMac;
    }

    public String getNameVehi() {
        return nameVehi;
    }

    public void setNameVehi(String nameVehi) {
        this.nameVehi = nameVehi;
    }

    public String getAddMac() {
        return addMac;
    }

    public void setAddMac(String addMac) {
        this.addMac = addMac;
    }
}
