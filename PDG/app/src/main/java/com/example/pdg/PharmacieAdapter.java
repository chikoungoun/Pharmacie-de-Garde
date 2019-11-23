package com.example.pdg;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

    /*   *** *** Fin ViewHolder *** ***  */



    private Context mContext;
    private List<Pharmacie> mPharmacies;

    private OnItemClickListener mListener;

    public PharmacieAdapter(Context mContext, List<Pharmacie> mPharmacies) {
        this.mContext = mContext;
        this.mPharmacies = mPharmacies;
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
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