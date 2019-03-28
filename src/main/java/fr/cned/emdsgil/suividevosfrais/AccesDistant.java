package fr.cned.emdsgil.suividevosfrais;

import android.util.Log;


import com.google.gson.JsonArray;

import fr.cned.emdsgil.suividevosfrais.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.AsyncResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class AccesDistant implements AsyncResponse {

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
            }
        }
    }

    public void envoi(String operation, String lesDonneesJSON, String login, String motdepasse){
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
       // accesDonnees.addParam("login", login);
       // accesDonnees.addParam("motdepasse", motdepasse);
      //  accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON);
        accesDonnees.execute(SERVERADDR);
    }
}
