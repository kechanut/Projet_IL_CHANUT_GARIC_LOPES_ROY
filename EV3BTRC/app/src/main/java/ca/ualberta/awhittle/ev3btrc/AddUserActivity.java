package ca.ualberta.awhittle.ev3btrc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddUserActivity extends AppCompatActivity {

    BT_Comm btComm;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser_rc);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final EditText pseudo = (EditText) findViewById(R.id.textLogin);
        final EditText mdp = (EditText) findViewById(R.id.textPassword);

        sharedpreferences = getSharedPreferences(getString(R.string.MyPREFERENCES), Context.MODE_PRIVATE);
        Log.d("SHARED PREFERENCES", "adduseractivity: "+ sharedpreferences);

        btComm = (BT_Comm) getIntent().getSerializableExtra("btcom");

        Log.d("BT COMM", "adduseractivity: "+btComm);

        // To notify user for permission to enable bt, if needed
        AlertDialog.Builder builder = new AlertDialog.Builder(AddUserActivity.this);

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



        if(!btComm.initBT()){
            // User did not enable Bluetooth

            Log.d("ON IF INITBT", "onCreate: ");
            btDisabledToast.show();
            Intent intent = new Intent(AddUserActivity.this, ConnectActivity.class);
            startActivity(intent);
        }
        Log.d("logggggg shared pref", "onCreate: "+ sharedpreferences.getString(getString(R.string.EV3KEY), ""));
        String macadress = sharedpreferences.getString(getString(R.string.EV3KEY), "");
        Boolean test = btComm.connectToEV3(macadress);
        Log.d("TEST DU TEST QUI TEST", "onCreate: "+ test);
        if(!btComm.connectToEV3(macadress)){
            //Cannot connect to given mac address, return to connect activity
            Log.d("ON IF SHAREDPREFERENCE", "onCreate: ");
            btFailedToast.show();
            Intent intent = new Intent(AddUserActivity.this, ConnectActivity.class);
            startActivity(intent);
        }

        final Button creerUser = (Button) findViewById(R.id.createUserButton);
        creerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pseudotostring = pseudo.getText().toString();
                String mdptostring = mdp.getText().toString();
                User newuser = new User(pseudotostring,mdptostring);

                try {
                    ArrayList<String> params = new ArrayList<>();
                    params.add(newuser.toString());
                    MessageBluetooth msg = new MessageBluetooth("newuser", params);
                    Log.d("MESSAGE", "onClick: "+msg.getParams());
                    btComm.writeMessage2(msg);
                    Log.d("Liste de user", ""+params);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
