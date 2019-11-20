package com.example.pdg;

import android.content.Context;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class PharmacieLoader extends AsyncTaskLoader<List<Pharmacie>> {


    private static final String LOG_TAG = PharmacieLoader.class.getName();

    private String url;

    public PharmacieLoader(Context context, String u) {
        super(context);

        url = u;
        Log.e("Loader", "Pharmacie Loader Constructor");
    }

    @Override
    protected void onStartLoading() {
        Log.e("Loader", "onStartLoading()");
        forceLoad();
    }

    @Nullable
    @Override
    public List<Pharmacie> loadInBackground() {
        Log.e("Loader", "LoadInBackground()");
        if (url == null) {
            return null;
        }

        return null;
    }

}