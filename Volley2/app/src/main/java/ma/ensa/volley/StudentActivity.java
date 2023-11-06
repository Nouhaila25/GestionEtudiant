package ma.ensa.volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


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


import ma.ensa.volley.Adapter.StudentAdapter;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText name, email, phone;
    private Button bnAdd;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> students;
    RequestQueue requestQueue;
    String insertUrl = "http://10.0.2.2:8089/api/student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Configurer la Toolbar comme ActionBar
        setSupportActionBar(toolbar);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        bnAdd = findViewById(R.id.bnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        students = new ArrayList<>();
        adapter = new StudentAdapter(students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        bnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name.getText().toString());
            jsonBody.put("email", email.getText().toString());

            // Lire l'entier depuis l'EditText phone
            String phoneText = phone.getText().toString();
            try {
                int phoneValue = Integer.parseInt(phoneText);
                jsonBody.put("phone", phoneValue);
            } catch (NumberFormatException e) {
                // Gérez l'exception si la conversion échoue
                e.printStackTrace();
            }
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


}