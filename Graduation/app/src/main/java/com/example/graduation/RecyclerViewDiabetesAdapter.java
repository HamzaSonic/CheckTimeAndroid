package com.example.graduation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewDiabetesAdapter extends RecyclerView.Adapter<RecyclerViewDiabetesAdapter.ViewHolder2> {
    private ArrayList<String> Dates= new ArrayList<>();
    private ArrayList<String> Times= new ArrayList<>();
    private ArrayList<String> results=new ArrayList<>();
    private Context mContext;


    public RecyclerViewDiabetesAdapter(Context mContext,ArrayList<String> dates, ArrayList<String> times, ArrayList<String> results) {
        Dates = dates;
        Times = times;
        this.results = results;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pressurelist,parent,false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder,final int position) {
        holder.dateDiabetes.setText(Dates.get(position));
        holder.dateDiabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, Dates.get(position), Toast.LENGTH_SHORT).show();
            }

        });
        holder.timeDiabetes.setText(Times.get(position));
        holder.timeDiabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, Times.get(position), Toast.LENGTH_SHORT).show();
            }

        });
        holder.resultDiabetes.setText(results.get(position));
        holder.resultDiabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, results.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView dateDiabetes;
        TextView timeDiabetes;
        TextView resultDiabetes;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            dateDiabetes = itemView.findViewById(R.id.date_pressure);
            timeDiabetes = itemView.findViewById(R.id.time_pressure);
            resultDiabetes = itemView.findViewById(R.id.result_pressure);

        }


    }
}
