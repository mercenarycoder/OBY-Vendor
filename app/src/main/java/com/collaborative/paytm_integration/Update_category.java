package com.collaborative.paytm_integration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Update_category extends AppCompatActivity {
String cat_id,name,position,status;
int position2=1;
ArrayList<String> list;
EditText category_name2;
Spinner spinner_state_update;
Button update_it;
SpinnerAdapter adapter;
ProgressDialog progressDialog;
ImageButton back_done_right45;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Intent intent=getIntent();
        cat_id=intent.getStringExtra("cat_id");
        name=intent.getStringExtra("name");
        position=intent.getStringExtra("position");
        status=intent.getStringExtra("status");
        if(status.equals("active"))
        {
            position2=0;
        }
        else
        {
            position2=1;
        }
        formlist();
        adapter=new SpinnerAdapter(Update_category.this,list);
        category_name2=(EditText)findViewById(R.id.category_name2);
        category_name2.setText(name);
        spinner_state_update=(Spinner)findViewById(R.id.spinner_state_update);
        spinner_state_update.setAdapter(adapter);
        spinner_state_update.setSelection(position2);
        spinner_state_update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status=list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back_done_right45=(ImageButton)findViewById(R.id.back_done_right45);
        back_done_right45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update_it=(Button)findViewById(R.id.update_category);
        update_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=category_name2.getText().toString();
                if(name.isEmpty())
                {
                    Toast.makeText(Update_category.this,"Please enter updating name",Toast.LENGTH_SHORT).show();
                }
                else
                {
            new updateCategory().execute();
                }
            }
        });
    }
    public void formlist()
    {
        list=new ArrayList<>();
        list.add("Active");
        list.add("Deactive");
    }
    public class updateCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Update_category.this);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/categoryupdate";
            String result=new JsonParser().updateCategory(url,cat_id,name,position,status);
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    String object1=String.valueOf(object.get("msg"));
                    //  Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_category.this);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                        finish();

                                }
                            });
                    builder.create();
                    builder.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Update_category.this,"Data inconsistent check please",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_category.this);
                builder.setTitle("Update")
                        .setMessage("Error updating data")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                builder.create();
                builder.show();
            }

        }
    }
}
