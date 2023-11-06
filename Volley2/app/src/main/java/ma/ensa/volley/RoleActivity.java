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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ensa.volley.Adapter.FiliereAdapter;
import ma.ensa.volley.Adapter.RoleAdapter;

public class RoleActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText name;
    private Button bnAdd,UserButton;
    RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RoleAdapter adapter;
    private List<Role> roles;
    String insertUrl = "http://10.0.2.2:8089/api/v1/roles";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Configurer la Toolbar comme ActionBar
        setSupportActionBar(toolbar);


        name = findViewById(R.id.name);

        bnAdd = findViewById(R.id.bnAdd);

        bnAdd.setOnClickListener(this);

        UserButton = findViewById(R.id.UserButton); // Initialiser roleButton
        recyclerView = findViewById(R.id.recyclerView);
        roles = new ArrayList<>();
        adapter = new RoleAdapter(roles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fetchDataFromServer();
        bnAdd.setOnClickListener(this);
        UserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoleActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

        //Filiere filiere = new Filiere(code.getText().toString(), libelle.getText().toString());
        //Gson toJson()

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name.getText().toString() );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response+"");
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
                            List<Role> roles = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Role role = new Role();
                                role.setName(jsonObject.getString("name"));

                                roles.add(role);
                            }

                            // Créez un adaptateur avec les données et définissez-le sur le RecyclerView
                            RoleAdapter adapter = new RoleAdapter(roles);
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