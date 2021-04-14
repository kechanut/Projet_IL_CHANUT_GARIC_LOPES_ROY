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


public class Utilisateur {

    public String nom;
    public String mdp;
    private static FileWriter fileWriter;

    public Utilisateur(String nom, String mdp) {
        this.nom = nom;
        this.mdp = mdp;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public String getPwd() {
        return mdp;
    }

    public void setPwd(String mdp) {
        this.mdp = mdp;
    }

    public void writeUtilisateur(Utilisateur user, String filename) {
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
        
		
        JSONObject UserDetail = new JSONObject();
        UserDetail.put("name", user.getName());
		UserDetail.put("mdp", user.getPwd());
        
        JSONObject User = new JSONObject();
        User.put("User", UserDetail);
	
        Array.add(User);
       
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
        System.out.println("L'utilisateur "+user.getName()+" a été ajouté");
    }
    public void readUtilisateur(Utilisateur user, String filename) throws JSONException{
        System.out.println("Lecture du fichier Utilisateurs");

    	JSONParser jsonParser = new JSONParser();
         
		try (FileReader reader = new FileReader("./"+filename+".json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray Users = (JSONArray) obj;
            System.out.println("array : "+Users);
             
            //Iterate over employee array
            for (Iterator iterator = Users.iterator(); iterator.hasNext();) {
            	JSONObject Iterationeur = (JSONObject) iterator.next();
            	JSONObject User = (JSONObject) Iterationeur.get("User");
            	System.out.println("User : "+User);
            	String name = ((String) User.get("name")).trim();
            	String mdp = ((String) User.get("mdp")).trim();
                System.out.println("Utlisateur name : "+name);
                System.out.println("Utlisateur mdp : "+mdp);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
/*		JSONObject jobjet = new JSONObject("User");
		JSONArray userList = (JSONArray) jobjet.get("User");
		System.out.println(userList);
		 
		for (int i=0; i < userList.length(); i++) {
			parseUserObject(userList.getJSONObject(i));
		}*/
    }
 
    private static void parseUserObject(JSONObject obj) throws JSONException 
    {
        //Get employee object within list
        JSONObject userObject;
	
		userObject = (JSONObject) obj.get("User");
		
         
        //Get employee first name
        String nom = (String) userObject.get("name");    
        System.out.println(nom);
         
        //Get employee last name
        String mdp = (String) userObject.get("mdp");  
        System.out.println(mdp);
 
    }
}