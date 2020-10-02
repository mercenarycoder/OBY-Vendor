package com.collaborative.paytm_integration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.parser.*;

public class MainActivity extends AppCompatActivity {
private Button login;
private Button signUp;
private EditText email,password;
private ProgressDialog dialog;
String email_getter,password_getter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    login=(Button)findViewById(R.id.login23);
    email=(EditText)findViewById(R.id.email);
    password=(EditText)findViewById(R.id.password);
    signUp=(Button)findViewById(R.id.sign_up);
    //sharedpreference will be created here
    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent in=new Intent(MainActivity.this,signup_Activity.class);
          startActivity(in);
          finish();
        }
    });
    Context context;
    SharedPreferences login_pref = getApplicationContext().
            getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);
    if(login_pref.contains("user_id")&&login_pref.contains("user_pass"))
    {
     String email_2=login_pref.getString("user_id",null);
     email.setText(email_2);
     String password_2=login_pref.getString("user_pass",null);
     password.setText(password_2);
     email_getter=email.getText().toString();
     password_getter=password.getText().toString();
     new AdminLogin().execute();
    }
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(email.getText().toString().length()==0)
            {
                email.setError("please enter email");
            }
            if(password.getText().toString().length()==0)
            {
                password.setError("please enter password");
            }
            email_getter=email.getText().toString();
     SharedPreferences p=getApplicationContext().getSharedPreferences("login_details",
             getApplicationContext().MODE_PRIVATE);
     SharedPreferences.Editor editor=p.edit();
            password_getter=password.getText().toString();
                  new AdminLogin().execute();
        }
    });
    }
 private class AdminLogin extends AsyncTask<String,String,String>
 {
     @Override
     protected void onPreExecute() {
         super.onPreExecute();
     Context context;
     dialog=new ProgressDialog(MainActivity.this);
     dialog.setMessage("Please wait");
     dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
     dialog.setCanceledOnTouchOutside(false);
     dialog.show();

     }

     @Override
     protected String doInBackground(String... strings) {
        JsonParser jsonParser=new JsonParser();
        String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/login";
        String json=jsonParser.userLoginFromJSON(url,email_getter,password_getter);
   //        Log.d("return",json);
        return json;
     }

     @Override
     protected void onPostExecute(String result) {
         super.onPostExecute(result);
         dialog.dismiss();
       //  Log.d("some",result);

         if(null!=result && !result.isEmpty())
        {
            try
            {
        JSONObject jsonObject = new JSONObject(result);

        String responce = String.valueOf(jsonObject.get("status"));
        String responce2=String.valueOf(jsonObject.get("msg"));
        Object value;
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        toDetails(jsonObject1);
        Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,Dashboard.class);
        intent.putExtra("json",result);
        startActivity(intent);
        finish();

        }
    catch (JSONException e) {
    e.printStackTrace();
        Toast.makeText(MainActivity.this,"please enter valid email and password",Toast.LENGTH_SHORT).show();
    }
    }
         //Toast.makeText(MainActivity.this, "something missing", Toast.LENGTH_SHORT).show();
    else
    {
    Toast.makeText(MainActivity.this,"please check details and try again",Toast.LENGTH_SHORT).show();
    }
     }
     public void toDetails(JSONObject jsonObject1) throws JSONException {
         SharedPreferences login_pref=getApplicationContext().getSharedPreferences("login_details",
                 getApplicationContext().MODE_PRIVATE);
         SharedPreferences.Editor editor=login_pref.edit();
         //Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
         editor.putString("user_pass",password_getter);
         editor.putString("user_id",email_getter);
         editor.putString("user_id",email.getText().toString());
         editor.putString("user_pass",password.getText().toString());
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
