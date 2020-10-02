package com.collaborative.paytm_integration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class Real_Addslots extends AppCompatActivity {
TextView days_selected;
EditText start_time_dalo2,end_time_dalo2;
Button save_and_see;
ImageButton back_done_right33;
String days[];
boolean selected[];
ArrayList<String> list,list2;
ProgressDialog progressDialog;
Spinner spinner_state;
SpinnerAdapter adapter;
String start_time_getter,end_time_getter,max_apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real__addslots);
        list=new ArrayList<>();
        formlist();
        days_selected=(TextView)findViewById(R.id.days_of_week);
        start_time_dalo2=(EditText)findViewById(R.id.start_time_dalo2);
        end_time_dalo2=(EditText)findViewById(R.id.end_time_dalo2);
        back_done_right33=(ImageButton)findViewById(R.id.back_done_right33);
        adapter=new SpinnerAdapter(Real_Addslots.this,list2);
        spinner_state=(Spinner)findViewById(R.id.spinner_state2);
        spinner_state.setAdapter(adapter);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back_done_right33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_and_see=(Button)findViewById(R.id.save_it_and2);
        dataLoader();
        save_and_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        days_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(Real_Addslots.this);
                mbuilder.setTitle("Select days");
                mbuilder.setMultiChoiceItems(days, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = mbuilder.create();
                alertDialog.show();
            }
        });
        start_time_dalo2.setInputType(InputType.TYPE_NULL);
        start_time_dalo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(Real_Addslots.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
                        , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        start_time_dalo2.setText(hourOfDay+":"+minute);
                    }
                }, 10, 20, DateFormat.is24HourFormat(Real_Addslots.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(Real_Addslots.this);
                tvTitle.setText("Choose Starting time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });
        end_time_dalo2.setInputType(InputType.TYPE_NULL);
        end_time_dalo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(Real_Addslots.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK
                        , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        end_time_dalo2.setText(hourOfDay+":"+minute);
                    }
                }, 10, 20, DateFormat.is24HourFormat(Real_Addslots.this));

                //You can set a simple text title for TimePickerDialog
                //tpd.setTitle("Title Of Time Picker Dialog");

                /*.........Set a custom title for picker........*/
                TextView tvTitle = new TextView(Real_Addslots.this);
                tvTitle.setText("Choose Ending time");
                tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
                tvTitle.setPadding(5, 3, 5, 3);
                tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
                tpd.setCustomTitle(tvTitle);
                tpd.show();
            }
        });
    }
    public void formlist()
    {
        list2=new ArrayList<>();
        list2.add("Status");
        list2.add("Active");
        list2.add("Deactive");
    }
    public void dataLoader()
    {
        days= new String[7];
        days[0]="Sunday";
        days[1]="Monday";
        days[2]="Tuesday";
        days[3]="Wednesday";days[4]="Thursday";days[5]="Friday";days[6]="Saturday";
        selected=new boolean[7];
        for(int i=0;i<selected.length;i++)
        {
            selected[i]=false;
        }
    }
    public class slotsAdder extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }
}
