package com.example.pdg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PharmacieAdapter.OnItemClickListener {


    public static final String SPEC_JSON = "https://raw.githubusercontent.com/GharWissen/PDG/master/casa_pdg.json";

    // message for missing internet connection
    private TextView emptyStateTextView;
    //Swipe to refresh
    private SwipeRefreshLayout swipeRefreshLayout;

    private PharmacieAdapter adapter;
    private ArrayList<Pharmacie> pharmaciesList;
    private RecyclerView rvPharmacies;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentSetter();

        Log.e("RequestQueue",""+mRequestQueue);


        // *** Connectivity Checker ***
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){

            emptyStateTextView.setText("");

            parseJSON();// -------------------------------------------------

        }else {

            emptyStateTextView.setText(R.string.no_internet_connection);
        }


        //Swipe to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               // Toast.makeText(MainActivity.this, "Inside the SwipeRefresher",Toast.LENGTH_SHORT).show();


                if(networkInfo != null && networkInfo.isConnectedOrConnecting()){


                    Log.e("Swiper","Connection available");

                    emptyStateTextView.setText("");
                    pharmaciesList.clear();
                    parseJSON();// -------------------------------------------------

                }else{

                    //Toast.makeText(MainActivity.this,"NOT CONNECTED SWIPE",Toast.LENGTH_SHORT).show();
                }
                pharmaciesList = new ArrayList<>();
                mRequestQueue = Volley.newRequestQueue(MainActivity.this);
                parseJSON();
                emptyStateTextView.setText("");


                swipeRefreshLayout.setRefreshing(false);
            }
        });



        // *** Ads part ***
        implementAds();


    }



    /*     */

    private void contentSetter(){

        emptyStateTextView = findViewById(R.id.empty_view);

        rvPharmacies = findViewById(R.id.rvPharmacies);

        rvPharmacies.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rvPharmacies.setLayoutManager(llm);

        pharmaciesList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);


    }

    /*     */

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

                                pharmaciesList.add(pharmacie);
                            }

                            adapter = new PharmacieAdapter(MainActivity.this,pharmaciesList);
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

    // Click + Geolocation
    @Override
    public void onItemClick(int position) {

        Pharmacie clickedItem = pharmaciesList.get(position);

        Toast.makeText(this,""+clickedItem.getCoordonnee(),Toast.LENGTH_SHORT).show();

        Uri gmmIntentUri = Uri.parse("geo:"+clickedItem.getCoordonnee()+"?z=13&q="+clickedItem.getNom()+","+clickedItem.getAdresse()+"+casablanca");

        // Testing the result
        Log.e("URI",""+gmmIntentUri);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);

    }


    public void implementAds(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView = (AdView)findViewById(R.id.adv1);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

    }






}
