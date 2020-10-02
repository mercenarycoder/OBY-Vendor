package com.collaborative.paytm_integration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;

public class Add_Services extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String cat_id=null;
    ImageView imageView_pic;
    LinearLayout takepic;
    Uri imageuri;
    EditText service_name,service_rate,service_description;
    String name=null,rate=null,description=null;
    Bitmap img=null;
    Button savencontinue,goback;
    ProgressDialog progressDialog;
    boolean conti=false;
    Context context;
    TextView selected_category;
    String cat_name="";
    ImageButton back_done_right99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__services);
        context=Add_Services.this;
        cat_id=getIntent().getStringExtra("cat_id");
        cat_name=getIntent().getStringExtra("cat_name");
        selected_category=(TextView)findViewById(R.id.selected_category);
        selected_category.setText(cat_name);
        imageView_pic=(ImageView)findViewById(R.id.imagepro2);
        takepic=(LinearLayout)findViewById(R.id.service_image2);
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        service_name=(EditText)findViewById(R.id.service_name);
        service_rate=(EditText)findViewById(R.id.service_rate);
        service_description=(EditText)findViewById(R.id.service_description);
        savencontinue=(Button)findViewById(R.id.save_service);
        goback=(Button)findViewById(R.id.back_service);
        back_done_right99=(ImageButton)findViewById(R.id.back_done_right99);
        back_done_right99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=service_name.getText().toString();
                rate=service_rate.getText().toString();
                description=service_description.getText().toString();
                if(!name.isEmpty()&&!rate.isEmpty())
                {
                    conti=true;
                    new Adding().execute();
                }
                else
                {
                    if(name.isEmpty()) {
                        service_name.setError("please enter service name");
                    }
                    else {
                        service_rate.setError("please enter rate first");
                    }
                }
            }
        });
        savencontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=service_name.getText().toString();
                rate=service_rate.getText().toString();
                description=service_description.getText().toString();
                if(!name.isEmpty()&&!rate.isEmpty())
                {
                    conti=false;
new Adding().execute();
                }
                else
                {
              if(name.isEmpty()) {
                  service_name.setError("please enter service name");
              }
              else {
                  service_rate.setError("please enter rate first");
              }
              }
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            imageuri=data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                imageView_pic.setImageBitmap(bitmap);
                img=bitmap;
                //imageView_pic.setImageURI(imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class Adding extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/servicesadd";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.addServices(url,name,rate,cat_id,img,description,staff_id);

            return data;
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
                    Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_Services.this);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(conti) {
                                        finish();
                                    }
                                    }
                            });
                    builder.create();
                    builder.show();
                    service_name.setText("");
                    service_rate.setText("");
                    service_description.setText("");
                    //make a default image and make it paste here
                } catch (JSONException e) {
                    e.printStackTrace();
                 System.out.println(e);
            Toast.makeText(Add_Services.this,"Some exception",Toast.LENGTH_SHORT).show();
                }
            }
            else if(isInternetAvailable()==false)
            {
                Toast.makeText(Add_Services.this,"Internet not available",Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Services.this);
                builder.setTitle("Update")
                        .setMessage("please check entered details.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        }
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
