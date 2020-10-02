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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;

public class update_Service extends AppCompatActivity {
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
    String prod_id,status_pro,hsn_id,convertImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__service);
        context=update_Service.this;

        Intent intent=getIntent();
        prod_id=intent.getStringExtra("prod_id");
        new getting().execute();
        selected_category=(TextView)findViewById(R.id.selected_category2);
        selected_category.setText(cat_name);
        imageView_pic=(ImageView)findViewById(R.id.imagepro22);
        takepic=(LinearLayout)findViewById(R.id.service_image22);
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        service_name=(EditText)findViewById(R.id.service_name2);
        service_rate=(EditText)findViewById(R.id.service_rate2);
        service_description=(EditText)findViewById(R.id.service_description2);
        savencontinue=(Button)findViewById(R.id.save_service2);
        savencontinue.setText("Update Service");
        //goback=(Button)findViewById(R.id.back_service);

        back_done_right99=(ImageButton)findViewById(R.id.back_done_right992);
        back_done_right99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class getting extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/servicesview";
            String data=new JsonParser().getProduct(url,prod_id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject object1=object.getJSONObject("data");
                    String service_name_final=String.valueOf(object1.get("product_name"));
                    String service_rate_final=String.valueOf(object1.get("rate"));
                    String service_id_final=String.valueOf(object1.get("category_id"));
                    String service_description_final=String.valueOf(object1.get("description"));
                    String service_status_final=String.valueOf(object1.get("status"));
                    String hsn_id_final=String.valueOf(object1.get("hsn_id"));
                    String url_img=String.valueOf(object1.get("img"));
                    hsn_id=hsn_id_final;
                    status_pro=service_status_final;
                    cat_id=service_id_final;
                    service_name.setText(service_name_final);
                    service_description.setText(service_description_final);
                    service_rate.setText(service_rate_final);
                /*    Bitmap bitmap=getBitmapFromURL(url_img);
                    if(bitmap!=null)
                    {
                        imageView_pic.setImageBitmap(bitmap);
                    }
                  */
                    // DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(update_Service.this,"Some error",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
Toast.makeText(update_Service.this,"NO internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }
            return null;
            } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private class Adding extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/servicesupdate";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.updateServices(url,prod_id,name,rate,description,convertImage,hsn_id,
                    cat_id,status_pro);

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(update_Service.this);
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
                    Toast.makeText(update_Service.this,"Some exception",Toast.LENGTH_SHORT).show();
                }
            }
            else if(isInternetAvailable()==false)
            {
                Toast.makeText(update_Service.this,"Internet not available",Toast.LENGTH_SHORT).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(update_Service.this);
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
