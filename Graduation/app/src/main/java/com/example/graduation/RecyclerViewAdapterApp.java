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

public class RecyclerViewAdapterApp extends RecyclerView.Adapter<RecyclerViewAdapterApp.ViewHolder3> {
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<String> datess=new ArrayList<>();
    private ArrayList<String> Locations=new ArrayList<>();
    private ArrayList<String> timess=new ArrayList<>();
    private ArrayList<String> notes=new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapterApp(Context mContext,ArrayList<String> dates, ArrayList<String> times, ArrayList<String> results,ArrayList<String> timess,ArrayList notes) {
        this.names = dates;
        this.datess = times;
        this.Locations = results;
        this.mContext = mContext;
        this.timess=timess;
        this.notes=notes;
    }


    @NonNull
    @Override
    public ViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appionlist,parent,false);
        return new ViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder3 holder,final int position) {
        holder.DocName.setText("Doctor:"+names.get(position));
        holder.DocDate.setText("on: "+datess.get(position));
        holder.DocLocation.setText("at: "+Locations.get(position));
        holder.timeapp.setText("time: "+timess.get(position));
        holder.notess.setText("notes: "+notes.get(position));

    }

    @Override
    public int getItemCount() {
        return datess.size();
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        TextView DocName;
        TextView DocDate;
        TextView DocLocation;
        TextView timeapp;
        TextView notess;

        public ViewHolder3(@NonNull View itemView) {
            super(itemView);
            DocName = itemView.findViewById(R.id.Doctor_name);
            DocDate = itemView.findViewById(R.id.appDate);
            DocLocation = itemView.findViewById(R.id.Location);
            timeapp=itemView.findViewById(R.id.timeApp);
            notess=itemView.findViewById(R.id.notessId);
        }


    }
}
