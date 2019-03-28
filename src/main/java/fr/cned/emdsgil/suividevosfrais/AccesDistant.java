package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import static fr.cned.emdsgil.suividevosfrais.Global.toast;

public class AccesDistant extends AppCompatActivity  implements AsyncResponse {

    private static final String SERVERADDR = "http://appliandroid.gael-peron-slam.fr/serveurppe.php";

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
                Serializer.serialize(Global.listFraisMois, Global.contextMainActivity);
                toast("Transfert des données réussi");
            }else {
                if (message[0].equals("visiteurNonReconnu")){
                    Log.d("erreurIdentifiants", "*********" + message[1]);
                    toast("Vos indentifiants ne sont pas reconnus, vérifier votre saisie");
                }
            }
        }else{
            toast("Connexion au serveur impossible, le transfert des données n'a pas été réalisé");
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
