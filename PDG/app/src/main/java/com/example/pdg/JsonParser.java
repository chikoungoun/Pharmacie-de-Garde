package com.example.pdg;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonParser {



    public static List<Pharmacie> parseJSON(String json_url){


        final ArrayList<Pharmacie> pharmacies = new ArrayList<Pharmacie>();

        JsonArrayRequest request = new JsonArrayRequest(json_url,
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

        return pharmacies;
    }

}
