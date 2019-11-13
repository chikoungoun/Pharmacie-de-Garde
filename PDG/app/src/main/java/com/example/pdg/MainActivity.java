package com.example.pdg;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PharmacieAdapter.OnItemClickListener {


    public static final String SPEC_JSON = "https://raw.githubusercontent.com/chikoungoun/Scraping/master/Pharmacies%20de%20garde/json_file/neo_pdg.json";

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

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rvPharmacies.setLayoutManager(llm);


       // rvPharmacies.addItemDecoration(new RV_Divider(getApplicationContext()));
        //rvPharmacies.addItemDecoration(new DividerItemDecoration(getApplicationContext(),0));


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

                                String nom = jObject.getString("pharmacie");
                                Log.e("SONIC",""+nom);
                                String quartier = jObject.getString("quartier");
                                String adresse = jObject.getString("adresse");
                                String telephone = jObject.getString("telephone");
                                String coordonnee = jObject.getString("coordonnee");



                                Pharmacie pharmacie = new Pharmacie(nom, quartier, adresse.toLowerCase(), telephone,coordonnee);

                                pharmacies.add(pharmacie);
                            }

                            adapter = new PharmacieAdapter(MainActivity.this,pharmacies);
                            rvPharmacies.setAdapter(adapter);

                            // Setting up a divider
                            //RecyclerView.ItemDecoration divider = new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL);
                            //rvPharmacies.addItemDecoration(divider);

                            // Ajout du ClickListener
                            adapter.setOnItemClickListener(MainActivity.this);


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

    @Override
    public void onItemClick(int position) {

        Pharmacie clickedItem = pharmacies.get(position);

        Toast.makeText(this,""+clickedItem.getCoordonnee(),Toast.LENGTH_SHORT).show();

        Uri gmmIntentUri = Uri.parse("geo:"+clickedItem.getCoordonnee()+"?z=13&q="+clickedItem.getNom()+",+casablanca");

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);

    }



}
