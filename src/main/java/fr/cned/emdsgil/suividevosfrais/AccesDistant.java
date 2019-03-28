package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import fr.cned.emdsgil.suividevosfrais.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.AsyncResponse;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;



public class AccesDistant extends AppCompatActivity  implements AsyncResponse {

    private static final String SERVERADDR = "http://192.168.1.12/ppeandroid/serveurppe.php";




    public AccesDistant() {
          super();
    }

    @Override
    public void processFinish(String output) {
        Log.d("serveur", "*********" + output);
        String[] message = output.split("%");
        if (message.length > 1){
            if (message[0].equals("synchronisation")){
                Log.d("enreg", "*********" + message[1]);
                Global.listFraisMois.clear();
                Log.d("test1", "TTTTEEEEEEEEEEEESSSSSTTTT5555");
                Serializer.serialize(Global.listFraisMois, Global.contextMainActivity);
                toast("Transfert des données réussi");







            }
        }
    }

    public void envoi(String operation, String lesDonneesJSON, String login, String motdepasse){

        Log.d("test1", "TTTTEEEEEEEEEEEESSSSSTTTT33333");
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
        accesDonnees.addParam("login", login);
        accesDonnees.addParam("motdepasse", motdepasse);
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON);
        accesDonnees.execute(SERVERADDR);

    }

    public void toast(String $textAAfficher){
        Toast toast= Toast.makeText(Global.contextSynchroniser,$textAAfficher, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
