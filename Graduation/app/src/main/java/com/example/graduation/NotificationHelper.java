package com.example.graduation;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.os.Build;


import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;


public class NotificationHelper extends ContextWrapper {
   // public static final String channelID = "channelID";
   // public static final String channelName = "Channel Name";
    static ArrayList<String> channelIDs=new ArrayList<>();
    static ArrayList<String> channelNames=new ArrayList<>();
    static NotificationChannel channels[]=new NotificationChannel[500];
    DatabaseHelper2 mydb;

    String id;
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
         id="channelID"+AddMedicine.i;
        channelIDs.add(id);
        String name="channel name "+AddMedicine.i;
        channelNames.add(name);

       // NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channels[AddMedicine.i]=new NotificationChannel(id,name,NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channels[AddMedicine.i]);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Calendar c=Calendar.getInstance();
        int hour= c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);
        String s=hour+":"+minute;
        mydb=new DatabaseHelper2(this);
        Cursor data=mydb.getDatabyTime(s);
        String name="",Dose="";
        int idd=0;
        int dosee=0;
        while (data.moveToNext()){
            name=data.getString(1);
            Dose+=data.getInt(2);
            idd=data.getInt(0);
            dosee=data.getInt(2);

        }
        mydb.deleteName(name,s);
        return new NotificationCompat.Builder(getApplicationContext(),id)
                .setContentTitle("Alarm!")
                .setContentText("Remember to take "+name +" with dose of "+Dose.charAt(0))
                .setSmallIcon(R.drawable.ic_pill);
    }
    public NotificationCompat.Builder getChannelNotification1() {

        DatabaseHelper3 mydb1=new DatabaseHelper3(this);
        Cursor data=mydb1.getData();
        String name="",Location="";
        String time=" ";
        while (data.moveToNext()){
            name=data.getString(1);
            Location=data.getString(2);
            time=data.getString(5);

        }

        return new NotificationCompat.Builder(getApplicationContext(),id)
                .setContentTitle("Remember to visit DR."+name)
                .setContentText("tomorrow at "+time +" in "+Location)
                .setSmallIcon(R.drawable.ic_doctor);
    }



}
