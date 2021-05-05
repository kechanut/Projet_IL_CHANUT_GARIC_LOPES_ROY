package ca.ualberta.awhittle.ev3btrc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RCActivity extends AppCompatActivity {

    BT_Comm btComm;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listenMessageFromRover();
        sendMessageToRover("i_detect_you");

        btComm = new BT_Comm();
        sharedPreferences = getSharedPreferences(getString(R.string.MyPREFERENCES), Context.MODE_PRIVATE);

        Log.d("BT COMM", "RCActivity: "+btComm);

        // To notify user for permission to enable bt, if needed
        AlertDialog.Builder builder = new AlertDialog.Builder(RCActivity.this);

        builder.setMessage(R.string.bt_permission_request);
        builder.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                btComm.setBtPermission(true);
                btComm.reply();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                btComm.setBtPermission(false);
                btComm.reply();
            }
        });

        // Create the AlertDialog
        AlertDialog btPermissionAlert = builder.create();

        Context context = getApplicationContext();
        //CharSequence text1 = getString(R.string.bt_enabled);
        CharSequence text1 = getString(R.string.bt_disabled);
        CharSequence text2 = getString(R.string.bt_failed);


        Toast btDisabledToast = Toast.makeText(context, text1, Toast.LENGTH_LONG);
        Toast btFailedToast = Toast.makeText(context, text2, Toast.LENGTH_LONG);

        //sharedPreferences = getSharedPreferences(getString(R.string.MyPREFERENCES), Context.MODE_PRIVATE);

        if(!btComm.initBT()){
            // User did not enable Bluetooth
            btDisabledToast.show();
            Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
            startActivity(intent);
        }

        if(!btComm.connectToEV3(sharedPreferences.getString(getString(R.string.EV3KEY), ""))){
            //Cannot connect to given mac address, return to connect activity
            btFailedToast.show();
            Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
            startActivity(intent);
        }

        // En cas de clic sur le bouton d'ouverture partielle
        final Button buttonOuverturePartielle = (Button) findViewById(R.id.buttonOuverturePartielle);
        buttonOuverturePartielle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ouverturePartielle();
            }
        });

        // En cas de clic sur le bouton d'ouverture totale
        final Button buttonOuvertureTotale = (Button) findViewById(R.id.buttonOuvertureTotale);
        buttonOuvertureTotale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ouvertureTotale();
            }
        });

        //Bouton permettant la déconnexion (pas encore implémenté)
        /*final Button buttonDeco = (Button) findViewById(R.id.button_deco);
        buttonDeco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    ArrayList<String> params = new ArrayList<>();
                    params.add("deconnexion");
                    MessageBluetooth msg = new MessageBluetooth("commande", params);
                    Log.d("MESSAGE DECONNEXION", "onClick: "+msg.getParams());

                    //On envoie le message a la brique
                    btComm.writeMessage2(msg);

                    Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/

        final EditText pseudo = (EditText) findViewById(R.id.textLogin);
        final EditText mdp = (EditText) findViewById(R.id.textPassword);

        //Bouton permettant la création d'un utilisateur
        final Button creerUser = (Button) findViewById(R.id.createUserButton);
        creerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //On récupère les éléments du formulaire
                String pseudotostring = pseudo.getText().toString();
                String mdptostring = mdp.getText().toString();
                User newuser = new User(pseudotostring, mdptostring);

                try {
                    //On créer un tableau de paramètres et on y rajoute les éléments
                    ArrayList<String> params = new ArrayList<>();
                    params.add(newuser.getName());
                    params.add(newuser.getPwd());

                    //On créer un nouveau message Bluetooth
                    MessageBluetooth msg = new MessageBluetooth("newuser", params);
                    Log.d("MESSAGE", "onClick: " + msg.getParams());

                    //On envoie le message a la brique
                    btComm.writeMessage2(msg);
                    Log.d("Liste de user", "" + params);

                    //On vide les champs du formulaire
                    pseudo.getText().clear();
                    mdp.getText().clear();
                    Toast.makeText(getApplicationContext(), "User créé", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        final EditText nameVehi = (EditText) findViewById(R.id.textNameVehi);
        final EditText addMac = (EditText) findViewById(R.id.textAddMac);

        //Bouton permettant la création d'un véhicule
        final Button creerVehicule = (Button) findViewById(R.id.createVehiculeButton);
        creerVehicule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //On récupère les éléments du formulaire
                String nameVehiToString = nameVehi.getText().toString();
                String addMacToString = addMac.getText().toString();
                Vehicule newvehicule = new Vehicule(nameVehiToString, addMacToString);

                try {
                    //On créer un tableau de paramètres et on y rajoute les éléments
                    ArrayList<String> params = new ArrayList<>();
                    params.add(newvehicule.getNameVehi());
                    params.add(newvehicule.getAddMac());

                    //On créer un nouveau message Bluetooth
                    MessageBluetooth msg = new MessageBluetooth("newvehicule", params);
                    Log.d("MESSAGE", "onClick: " + msg.getParams());

                    //On envoie le message a la brique
                    btComm.writeMessage2(msg);
                    Log.d("Liste de user", "" + params);

                    //On vide les champs du formulaire
                    nameVehi.getText().clear();
                    addMac.getText().clear();
                    Toast.makeText(getApplicationContext(), "Véhicule créé", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Gère l'envoi du message d'ouverture totale à la brique
    public void ouvertureTotale() {
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add("ouvTotale");
            MessageBluetooth msg = new MessageBluetooth("commande", params);
            Log.d("MESSAGE OUV TOTALE", "onClick: "+msg.getParams());
            btComm.writeMessage2(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Gère l'envoi du message d'ouverture partielle à la brique
    public void ouverturePartielle() {
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add("ouvPartielle");
            MessageBluetooth msg = new MessageBluetooth("commande", params);
            btComm.writeMessage2(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Gère l'envoi du message de fermeture à la brique (pas implémenté)
    /*public void fermeture_portail() {
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add("fermer"); // A Modifier
            MessageBluetooth msg = new MessageBluetooth("commande", params);
            btComm.writeMessage2(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    //Envoie un message sur firebase afin de prévenir le véhicule
    public void sendMessageToRover(String msg) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("portail");
        myRef.setValue(msg);
    }

    //Ecoute firebase afin de s'avoir si le véhicule souhaite ouvrir ou fermer le portail
    public void listenMessageFromRover() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rover");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("MESSAGE rover :", "Value is: " + value);
                Context context = getApplicationContext();

                if (!value.equals("null")) {
                    Toast m = Toast.makeText(context, "MESSAGE rover : "+value, Toast.LENGTH_LONG);
                    m.show();
                }
                sendMessageToRover("I receive : "+value);
                //Action en fonction de ce que souhaite le véhicule
                switch(value) {
                    case "ouverture_total" :
                        ouvertureTotale();
                        break;
                    case "ouverture_partielle" :
                        ouverturePartielle();
                        break;
                    case "Fermeture_portail" :
                        ouvertureTotale();
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MESSAGE rover", "Failed to read value.", error.toException());
            }
        });
    }
}
