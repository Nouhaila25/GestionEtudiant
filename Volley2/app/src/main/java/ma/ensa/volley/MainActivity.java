package ma.ensa.volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.volley.Adapter.FiliereAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText code, libelle;
    private Button bnAdd, roleButton;

    private RecyclerView recyclerView;
    private FiliereAdapter adapter;
    private List<Filiere> filieres;

    RequestQueue requestQueue;
    String insertUrl = "http://10.0.2.2:8089/api/v1/filieres";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Trouver la Toolbar par son ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Configurer la Toolbar comme ActionBar
        setSupportActionBar(toolbar);

        code = findViewById(R.id.code);
        libelle = findViewById(R.id.libelle);
        bnAdd = findViewById(R.id.bnAdd);

        roleButton = findViewById(R.id.roleButton);

        recyclerView = findViewById(R.id.recyclerView);
        filieres = new ArrayList<>();
        adapter = new FiliereAdapter(filieres);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fetchDataFromServer();

        bnAdd.setOnClickListener(this);

        roleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoleActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onClick(View view) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("code", code.getText().toString());
            jsonBody.put("libelle", libelle.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }
    private void fetchDataFromServer() {
        // Effectuer une requête GET pour récupérer les données depuis le service web
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, insertUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Filiere> filieres = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Filiere filiere = new Filiere();
                                filiere.setCode(jsonObject.getString("code"));
                                filiere.setLibelle(jsonObject.getString("libelle"));
                                filieres.add(filiere);
                            }

                            // Créez un adaptateur avec les données et définissez-le sur le RecyclerView
                            FiliereAdapter adapter = new FiliereAdapter(filieres);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Erreur", error.toString());
                    }
                }
        );
        requestQueue.add(request);

    }



}
