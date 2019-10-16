package com.example.pdg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String SPEC_JSON = "https://raw.githubusercontent.com/chikoungoun/Scraping/master/Pharmacies%20de%20garde/json_file/pharmacie_de_garde";

    private PharmacieAdapter adapter;
    private ArrayList<Pharmacie> pharmacies;
    private RecyclerView rvPharmacies;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPharmacies = findViewById(R.id.rvPharmacies);

        rvPharmacies.setHasFixedSize(true);

        rvPharmacies.setLayoutManager(new LinearLayoutManager(this));

        pharmacies = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        parseJSON();
    }

    private void parseJSON(){

        JsonArrayRequest request = new JsonArrayRequest(SPEC_JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jObject = jsonArray.getJSONObject(i);

                                String nom = jObject.getString("nom");
                                Log.e("SONIC",""+nom);
                                String quartier = jObject.getString("quartier");
                                String adresse = jObject.getString("adresse");
                                String telephone = jObject.getString("telephone");


                                Pharmacie pharmacie = new Pharmacie(nom, quartier, adresse.toLowerCase(), telephone);

                                pharmacies.add(pharmacie);
                            }

                            adapter = new PharmacieAdapter(MainActivity.this,pharmacies);
                            rvPharmacies.setAdapter(adapter);

                            RecyclerView.ItemDecoration divider = new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL);
                            rvPharmacies.addItemDecoration(divider);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
}
