package com.example.graduation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> Medicines =new ArrayList<>();
    private ArrayList<String>Next=new ArrayList<>();
    private ArrayList<Integer> dose=new ArrayList<>();
    private ArrayList<Integer>pills =new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter( Context mContext,ArrayList<String> medicines,ArrayList <Integer> dose,ArrayList<Integer>pills,ArrayList<String >Next ) {
        this.Medicines = medicines;
        this.Next=Next;
        this.dose=dose;
        this.pills=pills;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pillslist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(Medicines.get(position));
        holder.dose.setText("Dose: "+dose.get(position));
        holder.nextP.setText("Next pill at: "+Next.get(position));
        holder.left.setText("Pills Left: "+ pills.get(position));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,Medicines.get(position),Toast.LENGTH_SHORT).show();

            }
        });
      holder.cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
      });
    }

    @Override
    public int getItemCount() {
        return Medicines.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView dose;
        TextView left;
        TextView nextP;
        ConstraintLayout parent;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Medicine_name);
            dose=itemView.findViewById(R.id.MedDose);
            left=itemView.findViewById(R.id.MedPillsLeft);
            nextP=itemView.findViewById(R.id.MedNextTime);
            parent=itemView.findViewById(R.id.parentId);
            cardView=itemView.findViewById(R.id.cardViewMed);

        }
    }


}
