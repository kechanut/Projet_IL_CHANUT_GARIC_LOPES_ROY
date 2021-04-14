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
        
		
        JSONObject vehicleDetail = new JSONObject();
        vehicleDetail.put("name", vehicle.getName());
        vehicleDetail.put("mac", vehicle.getMac());
        
        JSONObject Vehicle = new JSONObject();
        Vehicle.put("Vehicle", vehicleDetail);
	
        Array.add(Vehicle);
       
        try {
            fileWriter = new FileWriter("./"+filename+".json");
            fileWriter.write(Array.toString());

        } catch (IOException e) {
            System.out.println("Erreur: impossible de créer le fichier '"
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
        System.out.println("L'utilisateur "+vehicle.getName()+" a été ajouté");
    }
    public void readVehicule(Vehicule vehicle, String filename) throws JSONException{
		JSONParser jsonParser = new JSONParser();
         
		try (FileReader reader = new FileReader("./"+filename+".json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray Vehicles = (JSONArray) obj;
            System.out.println("array : "+Vehicles);
             
            //Iterate over employee array
            for (Iterator iterator = Vehicles.iterator(); iterator.hasNext();) {
            	JSONObject Iterationeur = (JSONObject) iterator.next();
            	JSONObject Vehicle = (JSONObject) Iterationeur.get("Vehicle");
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
 
    private static void parseVehicleObject(JSONObject obj) throws JSONException 
    {
        
        JSONObject vehicleObject;
	
        vehicleObject = (JSONObject) obj.get("Vehicle");

        
        String nom = (String) vehicleObject.get("name");    
        System.out.println(nom);
         
       
        String mac = (String) vehicleObject.get("mac");  
        System.out.println(mac);
 
    }
}
