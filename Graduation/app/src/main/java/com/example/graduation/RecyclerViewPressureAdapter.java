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

public class RecyclerViewPressureAdapter extends RecyclerView.Adapter<RecyclerViewPressureAdapter.ViewHolder1> {

    private ArrayList<String> Dates= new ArrayList<>();
    private ArrayList<Integer> Times= new ArrayList<>();
    private ArrayList<Integer> results=new ArrayList<>();
    private Context mContext;

    public RecyclerViewPressureAdapter(Context mContext,ArrayList<String> dates, ArrayList<Integer> times, ArrayList<Integer> results ) {
        this.Dates = dates;
        this.Times = times;
        this.results = results;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewPressureAdapter.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pressurelist,parent,false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder,final int position) {
        holder.datePressure.setText(Dates.get(position));
        holder.datePressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, Dates.get(position), Toast.LENGTH_SHORT).show();
            }

        });
        holder.timePressure.setText(Times.get(position).toString());
        holder.timePressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, Times.get(position), Toast.LENGTH_SHORT).show();
            }

        });
        holder.resultPressure.setText(results.get(position).toString());
        holder.resultPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, results.get(position), Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public int getItemCount() {
        return Dates.size();
    }
    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView datePressure;
        TextView timePressure;
        TextView resultPressure;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            datePressure = itemView.findViewById(R.id.date_pressure);
            timePressure = itemView.findViewById(R.id.time_pressure);
            resultPressure = itemView.findViewById(R.id.result_pressure);

        }
    }

}

