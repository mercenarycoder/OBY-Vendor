package com.collaborative.paytm_integration;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class signup_Activity extends AppCompatActivity {
private Spinner spn_category,spn_city;
private SpinnerAdapter2 category,city;
ArrayList<SpinnerClass> cities;
ArrayList<SpinnerClass> categories;
EditText name,email,phone;
String name_get,email_get,phone_get;
private Button sign_up,go_back;
String ids_category=null,name_category=null;
String ids_city=null,name_city=null;
String city_select_id,category_select_id;
ProgressDialog dialog;
//Button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        new fromInformation().execute();
        new fromInformation2().execute();
        name=(EditText)findViewById(R.id.sign_up_name);
        email=(EditText)findViewById(R.id.sign_up_email);
        phone=(EditText)findViewById(R.id.sign_up_number);
        sign_up=(Button)findViewById(R.id.sign_up_button);
        go_back=(Button)findViewById(R.id.sign_up_go_back);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        name_get=name.getText().toString();
        email_get=email.getText().toString();
        phone_get=phone.getText().toString();
        if(!name_get.isEmpty()&&!email_get.isEmpty()&&!phone_get.isEmpty())
        {
         new AdminLogin().execute();
        }
        else
        {
            Toast.makeText(signup_Activity.this,"please enter data in all fields",Toast.LENGTH_SHORT).show();
        }
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(signup_Activity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
    private class fromInformation extends AsyncTask<String,String,String>
    {

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            try
            {
                URL url2 = new URL("https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/business_category");
                httpURLConnection=(HttpURLConnection)url2.openConnection();
                httpURLConnection.setRequestMethod("GET");
                String current="";
                InputStream ir=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(ir);
                int data = inputStreamReader.read();
                while (data != -1) {
                    current += (char) data;
                    data = inputStreamReader.read();
                    System.out.print(current);
                }
                JSONObject object=new JSONObject(current);
                cities=new ArrayList<>();
                JSONArray jsonArray=object.getJSONArray("data");
                cities.add(new SpinnerClass("krishna","Bussniess Category"));
                for(int i=0;i<jsonArray.length();i++)
                {
                 JSONObject object1 = jsonArray.getJSONObject(i);
                 cities.add(new SpinnerClass(String.valueOf(object1.get("id")),
                         String.valueOf(object1.get("name"))));
                }
                        } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection!=null)
                {
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            spn_city=(Spinner)findViewById(R.id.sign_up_city);
            city=new SpinnerAdapter2(signup_Activity.this,cities);
            spn_city.setAdapter(city);
            spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerClass spinnerClass=cities.get(position);
                    category_select_id=spinnerClass.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            super.onPostExecute(s);
        }
    }
    private class fromInformation2 extends AsyncTask<String,String,String>
    {

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            try
            {
                URL url2 = new URL("https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/city");
                httpURLConnection=(HttpURLConnection)url2.openConnection();
                httpURLConnection.setRequestMethod("GET");
                String current="";
                InputStream ir=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(ir);
                int data = inputStreamReader.read();
                while (data != -1) {
                    current += (char) data;
                    data = inputStreamReader.read();
                    System.out.print(current);
                }
                JSONObject object=new JSONObject(current);
                categories=new ArrayList<>();
                JSONArray jsonArray=object.getJSONArray("data");
                categories.add(new SpinnerClass("krishna","City"));
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    categories.add(new SpinnerClass(String.valueOf(object1.get("id")),
                            String.valueOf(object1.get("name"))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection!=null)
                {
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //spn_city=(Spinner)findViewById(R.id.sign_up_city);
            //city=new SpinnerAdapter2(signup_Activity.this,cities);
            //spn_city.setAdapter(city);
             spn_category=(Spinner)findViewById(R.id.sign_up_category);
            category=new SpinnerAdapter2(signup_Activity.this,categories);
            spn_category.setAdapter(category);
            spn_category.setPrompt("City");
            spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   SpinnerClass spinnerClass=categories.get(position);
                   city_select_id=spinnerClass.getId();
                  //Toast.makeText(signup_Activity.this,category_select_id,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    private class AdminLogin extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
            dialog=new ProgressDialog(signup_Activity.this);
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            JsonParser jsonParser=new JsonParser();
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/businessSignup";
            String json=jsonParser.signingIn(url,name_get,email_get,phone_get,city_select_id,category_select_id);
            //        Log.d("return",json);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            //  Log.d("some",result);

            if(null!=result)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(result);

                    String responce = String.valueOf(jsonObject.get("status"));
                    String responce2=String.valueOf(jsonObject.get("msg"));
                    Object value;
                   // JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    //toDetails(jsonObject1);
                    //Toast.makeText(signup_Activity.this,jsonObject1.getString("user_name"),Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(signup_Activity.this);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                      Intent intent=new Intent(signup_Activity.this,MainActivity.class);
                                   // intent.putExtra("json",result);
                      startActivity(intent);
                      Toast.makeText(signup_Activity.this, "Successfull now login", Toast.LENGTH_SHORT).show();
                      finish();

                                }
                            });
                    builder.create();
                    builder.show();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(signup_Activity.this);
                    builder.setTitle("Update")
                            .setMessage("Something went wrong with information!!")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
            //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(signup_Activity.this,"please check details and try again",Toast.LENGTH_SHORT).show();
            }
        }
        public void toDetails(JSONObject jsonObject1) throws JSONException {
            SharedPreferences login_pref=getApplicationContext().getSharedPreferences("login_details",
                    getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor=login_pref.edit();
            //Toast.makeText(signup_Activity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
            //editor.putString("user_pass",password_getter);
            editor.putString("user_id",email_get);
            editor.putString("user_id",email.getText().toString());
            //editor.putString("user_pass",password.getText().toString());
            editor.putString("auth_key", String.valueOf(jsonObject1.get("auth_key")));
            editor.putString("user_name", String.valueOf(jsonObject1.get("user_name")));
            editor.putString("user_mobile",String.valueOf(jsonObject1.get("user_mobile")));
            editor.putString("user_state", String.valueOf(jsonObject1.get("user_state")));
            editor.putString("user_address", String.valueOf(jsonObject1.get("user_address")));
            editor.putString("user_role", String.valueOf(jsonObject1.get("user_role")));
            editor.putString("user_city", String.valueOf(jsonObject1.get("user_city")));
            editor.putString("user_id2", String.valueOf(jsonObject1.get("id")));
            editor.putString("user_lang", String.valueOf(jsonObject1.get("user_lang")));
            editor.apply();
        }
    }

}
