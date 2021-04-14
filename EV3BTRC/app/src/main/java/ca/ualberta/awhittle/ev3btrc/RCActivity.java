package ca.ualberta.awhittle.ev3btrc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
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

        final Button buttonOuverturePartielle = (Button) findViewById(R.id.buttonOuverturePartielle);
        buttonOuverturePartielle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              /*  try {
                    btComm.writeMessage((byte) 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */

                try {
                    ArrayList<String> params = new ArrayList<>();
                    params.add("ouvPartielle");
                    MessageBluetooth msg = new MessageBluetooth("commande", params);
                    btComm.writeMessage2(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        final Button buttonOuvertureTotale = (Button) findViewById(R.id.buttonOuvertureTotale);
        buttonOuvertureTotale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* try {
                    btComm.writeMessage((byte) 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

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
        });

        /*final Button buttonDeco = (Button) findViewById(R.id.button_deco);
        buttonDeco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    ArrayList<String> params = new ArrayList<>();
                    params.add("deconnexion");
                    MessageBluetooth msg = new MessageBluetooth("commande", params);
                    Log.d("MESSAGE DECONNEXION", "onClick: "+msg.getParams());
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

        final Button creerUser = (Button) findViewById(R.id.createUserButton);
        creerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pseudotostring = pseudo.getText().toString();
                String mdptostring = mdp.getText().toString();
                User newuser = new User(pseudotostring,mdptostring);


                try {
                    ArrayList<String> params = new ArrayList<>();
                    params.add(newuser.getName());
                    params.add(newuser.getPwd());
                    MessageBluetooth msg = new MessageBluetooth("newuser", params);
                    Log.d("MESSAGE", "onClick: "+msg.getParams());
                    btComm.writeMessage2(msg);
                    Log.d("Liste de user", ""+params);

                    pseudo.getText().clear();
                    mdp.getText().clear();
                    Toast.makeText(getApplicationContext(), "User créé", Toast.LENGTH_SHORT).show();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        final EditText nameVehi = (EditText) findViewById(R.id.textNameVehi);
        final EditText addMac = (EditText) findViewById(R.id.textAddMac);

        final Button creerVehicule = (Button) findViewById(R.id.createVehiculeButton);
        creerVehicule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nameVehiToString = nameVehi.getText().toString();
                String addMacToString = addMac.getText().toString();
                Vehicule newvehicule = new Vehicule(nameVehiToString,addMacToString);


                try {
                    ArrayList<String> params = new ArrayList<>();
                    params.add(newvehicule.getNameVehi());
                    params.add(newvehicule.getAddMac());
                    MessageBluetooth msg = new MessageBluetooth("newvehicule", params);
                    Log.d("MESSAGE", "onClick: "+msg.getParams());
                    btComm.writeMessage2(msg);
                    Log.d("Liste de user", ""+params);

                    nameVehi.getText().clear();
                    addMac.getText().clear();
                    Toast.makeText(getApplicationContext(), "Véhicule créé", Toast.LENGTH_SHORT).show();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
