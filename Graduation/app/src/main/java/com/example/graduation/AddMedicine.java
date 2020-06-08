package com.example.graduation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddMedicine extends AppCompatActivity {
    private Button save;
    AutoCompleteTextView name,dose,pills,times;
    TextView time;
    DatabaseHelper2 mydb;
    Calendar cur_calender;
    public static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb=new DatabaseHelper2(this);
        setContentView(R.layout.activity_add_medicine);
        name=findViewById(R.id.medName);
        dose=findViewById(R.id.doseId);
        pills=findViewById(R.id.pillsLeftId);
        times=findViewById(R.id.timesPerDayID);
        time= findViewById(R.id.timeId);
        save=findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medName,timefirst;
                Integer dose1 , pills1, times1;
                medName=name.getText().toString();
                timefirst=time.getText().toString();
                String dose11=dose.getText().toString(),pills11=pills.getText().toString(),times11=times.getText().toString();
                if (medName.length() !=0 && timefirst.length()!=0 && dose11.length()!=0 && pills11.length() !=0 && times11.length()!=0 ){
                    dose1=Integer.parseInt(dose11);
                    pills1=Integer.parseInt(pills11);
                    times1=Integer.parseInt(times11);
                    int difference= 24/times1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Calendar c=Calendar.getInstance();
                        int z=getHour(timefirst);
                        int zz=getMinute(timefirst);
                        c.set(Calendar.HOUR_OF_DAY,z);
                        c.set(Calendar.MINUTE,zz);
                        c.set(Calendar.SECOND,0);
                        startAlarm(c,difference);
                    }

                      while (pills1>0){
                          mydb.addData(medName,dose1,pills1,times1,timefirst);
                        pills1-=dose1;
                        int a=(getHour(timefirst)+difference)%24;
                        timefirst=a+timefirst.substring(timefirst.indexOf(':'));

                    }
                    i++;
                    Intent intent =new Intent(AddMedicine.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddMedicine.this, "please fill all the fields! ",Toast.LENGTH_SHORT).show();
                }

            }
        });
        initComponent();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c,int diff) {
        Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_HOUR*diff,pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

    }

    private void initComponent() {
        (findViewById(R.id.bt_pick)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTimePickerLight();
            }
        });
    }

    private void dialogTimePickerLight() {
         cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                ((TextView) findViewById(R.id.timeId)).setText(hourOfDay + ":" + minute);
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);

        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }
    public int getHour(String hour){
        int x=2;
        for(int i=0;i<hour.length();i++)
            if (hour.charAt(i)==':') x=i;
            Integer a=Integer.parseInt(hour.substring(0,x));
            return a;
    }
    public int getMinute (String hour){
        Integer a= Integer.parseInt(hour.substring(hour.indexOf(':')+1));
        return a;
    }




}
