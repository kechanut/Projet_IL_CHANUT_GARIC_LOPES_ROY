package portailEV3.hardware;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.io.FileReader;

public class Vehicule {

    public String nom;
    public String admac;
    private static FileWriter fileWriter;

    public Vehicule(String nom, String admac) {
        this.nom = nom;
        this.admac = admac;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public String getMac() {
        return admac;
    }

    public void setMac(String admac) {
        this.admac = admac;
    }
    
    //Ecriture du v�hicule dans le fichier JSon
    public void writeVehicule(Vehicule vehicle, String filename) {
        
        JSONArray Array = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        
		try {
			Object obj = jsonParser.parse(new FileReader("./"+filename+".json"));
			Array = (JSONArray) obj;
			
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
		//r�cup�ration des donn�es 
        JSONObject vehicleDetail = new JSONObject();
        vehicleDetail.put("name", vehicle.getName());
        vehicleDetail.put("mac", vehicle.getMac());
        
        //cr�ation de l'objet avec les donn�es
        JSONObject Vehicle = new JSONObject();
        Vehicle.put("Vehicle", vehicleDetail);

        //Ajout du v�hicule dans le tableau dans le fichier JSon
        Array.add(Vehicle);
       
        
        //Cr�ation du fichier JSon si il n'existe pas
        try {
            fileWriter = new FileWriter("./"+filename+".json");
            fileWriter.write(Array.toString());

        } catch (IOException e) {
            System.out.println("Erreur: impossible de cr�er le fichier '"
                    );
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("L'utilisateur "+vehicle.getName()+" a �t� ajout�");
    }
    
    //LEcture du fichier JSon des v�hicules
    public void readVehicule(Vehicule vehicle, String filename) throws JSONException{
		JSONParser jsonParser = new JSONParser();
         
		try (FileReader reader = new FileReader("./"+filename+".json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray Vehicles = (JSONArray) obj;
            System.out.println("array : "+Vehicles);
             
            //Iteration du tableau des v�hicules dans le fichier Json
            for (Iterator iterator = Vehicles.iterator(); iterator.hasNext();) {
            	JSONObject Iteration = (JSONObject) iterator.next();
            	JSONObject Vehicle = (JSONObject) Iteration.get("Vehicle");
            	System.out.println("Vehicle : "+Vehicle);
            	String name = ((String) Vehicle.get("name")).trim();
            	String mac = ((String) Vehicle.get("mac")).trim();
                System.out.println("Vehicule name : "+name);
                System.out.println("Vehicule mac : "+mac);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
    }
}
