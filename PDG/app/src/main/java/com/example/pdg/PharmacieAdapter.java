package com.example.pdg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PharmacieAdapter  extends RecyclerView.Adapter<PharmacieAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomTextView;
        public TextView quartierTextView;
        public TextView adresseTextView;
        public TextView telephoneTextView;


        public ViewHolder(View itemView) {
            super(itemView);

            nomTextView = (TextView) itemView.findViewById(R.id.nom);
            quartierTextView = (TextView) itemView.findViewById(R.id.quartier);
            adresseTextView = (TextView) itemView.findViewById(R.id.adresse);
            telephoneTextView = (TextView) itemView.findViewById(R.id.telephone);

        }

    }

    private List<Pharmacie> mPharmacies;

    public PharmacieAdapter(MainActivity mainActivity, List<Pharmacie> mPharmacies) {
        this.mPharmacies = mPharmacies;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        // Get the data model based on position
        Pharmacie pharmacie = mPharmacies.get(position);

        // Set item views based on your views and data model
        TextView nom = viewHolder.nomTextView;
        nom.setText(pharmacie.getNom());

        TextView quartier = viewHolder.quartierTextView;
        quartier.setText(pharmacie.getQuartier());

        TextView adresse = viewHolder.adresseTextView;
        adresse.setText(pharmacie.getAdresse());

        TextView telephone = viewHolder.telephoneTextView;
        telephone.setText(pharmacie.getTelephone());

    }

    @Override
    public int getItemCount() {
        return mPharmacies.size();
    }
}