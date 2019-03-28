package fr.cned.emdsgil.suividevosfrais;

import android.util.Log;


import com.google.gson.JsonArray;

import fr.cned.emdsgil.suividevosfrais.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.AsyncResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static fr.cned.emdsgil.suividevosfrais.Global.listFraisMois;

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
            }else{
                if (message[0].equals("miseajour")){
                    Log.d("miseAJour", "*********" + message[1]);
                    try {
                        //FraisMois lesFraisRecus = new FraisMois;

                        JSONArray lesInfos = new JSONArray(message[1]);

                        //raisMois fraisMisAJour = new FraisMois(annee,  mois);

                        String anneeString = lesInfos.getString(0);
                        Integer anneeInteger = Integer.parseInt(anneeString);
                        String moisString = lesInfos.getString(1);
                        Integer moisInteger = Integer.parseInt(moisString);
                        String keyString = anneeString + moisString;
                        Integer keyInteger = Integer.parseInt(keyString);

                        JSONArray fraisforfait = lesInfos.getJSONArray(2);

                        Log.d("stop", "STOOOOOOP");


                        /*
                        for (int i = 0; i < fraisforfait.length; i++) {
                            System.out.println(hobbits[i]);
                        }*/


                        /*
                        if (!Global.listFraisMois.containsKey(keyInteger)) {
                            // creation du mois et de l'annee s'ils n'existent pas déjà
                            Global.listFraisMois.put(keyInteger, new FraisMois(anneeInteger, moisInteger)) ;
                        }



                        Global.listFraisMois.get(keyInteger).setNuitee(Integer.parseInt(lesInfos.)) ;
                        Global.listFraisMois.get(keyInteger).setEtape();
                        Global.listFraisMois.get(keyInteger).setKm();
                        Global.listFraisMois.get(keyInteger).setRepas();

*/


















                        Log.d("stop", "STOOOOOOP");

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void envoi(String operation, String lesDonneesJSON, String login, String motdepasse){
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
        accesDonnees.addParam("login", login);
        accesDonnees.addParam("motdepasse", motdepasse);
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON);
        accesDonnees.execute(SERVERADDR);
    }
}
