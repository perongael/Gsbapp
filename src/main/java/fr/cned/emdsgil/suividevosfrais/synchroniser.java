package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.cned.emdsgil.suividevosfrais.Global.listFraisMois;

public class synchroniser extends AppCompatActivity {


    private static AccesDistant accesDistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchroniser);
        setTitle("GSB : Synchronisation des frais");
        imgReturn_clic() ;
        lancer_synchronisation_clic();
    }
/**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.retour_accueil))) {
            retourActivityPrincipale() ;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        findViewById(R.id.imgSynchroniserReturn).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(synchroniser.this, MainActivity.class) ;
        startActivity(intent) ;
    }

    private void lancer_synchronisation_clic() {
        findViewById(R.id.cmdsynchroniser).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // envoi les informations sérialisées vers le serveur
                // en construction

                final Gson gson = new GsonBuilder().serializeNulls().create();

               String result = gson.toJson(listFraisMois);

                List uneliste = new ArrayList(listFraisMois.values());

                JSONObject obj = new JSONObject();


                JsonArray lesDonnees = new Gson().toJsonTree(uneliste).getAsJsonArray();

                String login =((EditText)findViewById(R.id.txtidentifiantARemplir)).getText().toString();

                String motdepasse =((EditText)findViewById(R.id.txtmotdepasseARemplir)).getText().toString();

                String lesDonneesJSON = lesDonnees.toString();

                accesDistant = new AccesDistant();
                        accesDistant.envoi("synchronisation", lesDonneesJSON, login, motdepasse);


            }
        });
    }
}
