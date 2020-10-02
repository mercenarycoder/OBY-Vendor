package com.collaborative.paytm_integration;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Services_Fragement extends Fragment {
Spinner spinner;
private ArrayList<SpinnerClass> list;
private SpinnerAdapter2 category,city;
private SpinnerAdapter2 service_at;
ArrayList<SpinnerClass> cities;
ArrayList<SpinnerClass> categories;
private String state;
EditText company_name,company_address,company_number,company_tandc;
ImageView imagepro;
TextView imagepro2,ek_alert_dkho;
LinearLayout company_image,company_document,hide_n_seek1,hide_n_seek2;
Button save_services;
URL url_image;
Uri imageuri,doc_uri;
Bitmap img;
Switch check_on_off;
Context context;
String id_city;
getSettingClass refrence;
String servises[],servises2[],name="",address="",number="",tnc="",bus_cat="",ser_at="",on_off="",
        path_document="",convertImage="";
boolean services_id[],servises_id2[];
int int_ids_bus[];
setServices updater;
    private static final int FILE_SELECT_CODE = 0;
    private int PICK_IMAGE_REQUEST=1;
    ProgressDialog progressDialog;
    private String category_select_id;

    //use getSupportinfo of JSonparser class to get the deatils initially
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_fragment, container, false);
        context=this.getActivity();
     //from here ids are given
        check_on_off=(Switch)view.findViewById(R.id.check_on_off);
        check_on_off.setChecked(false);
        company_name=(EditText)view.findViewById(R.id.company_name);
        company_address=(EditText)view.findViewById(R.id.company_address);
        company_number=(EditText)view.findViewById(R.id.company_number);
        company_tandc=(EditText)view.findViewById(R.id.company_tandc);
        imagepro=(ImageView)view.findViewById(R.id.imagepro);
       ek_alert_dkho=(TextView)view.findViewById(R.id.ek_alert_dkho);
       ek_alert_dkho.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Information")
                       .setMessage(R.string.app_enable)
                       .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
               builder.create();
               builder.show();
           }
       });
        imagepro2=(TextView)view.findViewById(R.id.imagepro2);
        company_image=(LinearLayout)view.findViewById(R.id.company_image);
        company_document=(LinearLayout)view.findViewById(R.id.company_document);
        save_services=(Button)view.findViewById(R.id.save_services);
        hide_n_seek1=(LinearLayout)view.findViewById(R.id.hide_n_seek1);
        hide_n_seek2=(LinearLayout)view.findViewById(R.id.hide_n_seek2);
        spinner=(Spinner)view.findViewById(R.id.company_spinner);
     //and initialization ends here
      new fromInformation().execute();
      new fromInformation2().execute();
      new getSetting().execute();
      company_image.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openFileChooser();
          }
      });
      check_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(!isChecked)
              {
                  hide_n_seek2.setEnabled(false);
                  hide_n_seek1.setEnabled(false);
                  hide_n_seek1.setVisibility(View.INVISIBLE);
                  hide_n_seek2.setVisibility(View.INVISIBLE);
              }
              else
              {
                  hide_n_seek2.setEnabled(true);
                  hide_n_seek1.setEnabled(true);
                  hide_n_seek1.setVisibility(View.VISIBLE);
                  hide_n_seek2.setVisibility(View.VISIBLE);
              }
          }
      });
      if(check_on_off.isChecked())
      {
          hide_n_seek1.setVisibility(View.VISIBLE);
          hide_n_seek2.setVisibility(View.VISIBLE);
          hide_n_seek2.setEnabled(true);
          hide_n_seek1.setEnabled(true);
      }
      else
      {
          hide_n_seek1.setVisibility(View.INVISIBLE);
          hide_n_seek2.setVisibility(View.INVISIBLE);
          hide_n_seek2.setEnabled(false);
          hide_n_seek1.setEnabled(false);

      }
               save_services.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             name=company_name.getText().toString();
             address=company_address.getText().toString();
             number=company_number.getText().toString();
             tnc=company_tandc.getText().toString();
             for(int i=0;i<servises_id2.length;i++)
             {
                 if(servises_id2[i])
                 {
                     ser_at+=String.valueOf(i+1);
                     if(i+1!=servises_id2.length) {
                         ser_at += ",";
                     }
                 }
             }
             if(check_on_off.isChecked())
             {
                 on_off="1";
             }
             else
             {
                 on_off="0";
             }
             for(int i=0;i<services_id.length;i++)
             {
                 if(services_id[i])
                 {
                     bus_cat+=int_ids_bus[i];
                     if(i+1!=services_id.length)
                     {
                         bus_cat+=",";
                     }
                 }
             }
           //    Toast.makeText(context, ser_at, Toast.LENGTH_SHORT).show();
             if(on_off.isEmpty()||name.isEmpty()||address.isEmpty()||number.isEmpty()||tnc.isEmpty()||bus_cat.isEmpty())
             {
                 Toast.makeText(context,"Please fill details something missing",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 updater=new setServices();
                 updater.execute();
             }

           }
       });
       company_document.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
               intent.setType("*/*");
               intent.addCategory(Intent.CATEGORY_OPENABLE);

               try {
                   startActivityForResult(
                           Intent.createChooser(intent, "Select a File to Upload"),
                           FILE_SELECT_CODE);
               } catch (android.content.ActivityNotFoundException ex) {
                   // Potentially direct the user to the Market with a Dialog
                   Toast.makeText(context, "Please install a File Manager.",
                           Toast.LENGTH_SHORT).show();
               }
           }
       });
        return  view;
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            imageuri=data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageuri);
                //imagepro.setImageBitmap(bitmap);
                img=bitmap;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                convertImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
                System.out.println(convertImage);
                byte [] imageAsBytes=Base64.decode(convertImage.getBytes(),Base64.DEFAULT);
                imagepro.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length));
                //imageView_pic.setImageURI(imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==FILE_SELECT_CODE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
         doc_uri=data.getData();
         try {
             path_document=getPath(context,doc_uri);
             imagepro2.setText(path_document);
           //Some more code will be added here
         } catch (Exception e) {
             e.printStackTrace();
         }
        }
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public class getSetting extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/getsetting";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.getSupportInfo(url,staff_id);

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
                    JSONObject dataObj=object.getJSONObject("data");
                    String cmp_name=String.valueOf(dataObj.get("cmp_name"));
                    company_name.setText(cmp_name);
                    String cmp_address=String.valueOf(dataObj.get("address"));
                    company_address.setText(cmp_address);
                    String cmp_no=String.valueOf(dataObj.get("contact_no"));
                    company_number.setText(cmp_no);
                    String tnc=String.valueOf(dataObj.get("term_condition"));
                    company_tandc.setText(tnc);
                    String url=String.valueOf(dataObj.get("logo"));
                    String bussniess_cat=String.valueOf(dataObj.get("business_category"));
                    String service_at_cat=String.valueOf(dataObj.get("service_at"));
                    String bussniess_status=String.valueOf(dataObj.get("business_status"));
                    if(bussniess_status.equals("1"))
                    {
                        check_on_off.setChecked(true);
                    }
                    else
                    {
                        check_on_off.setChecked(false);
                    }
                    String cmp_id=String.valueOf(dataObj.get("cmp_id"));
                    String city_name=String.valueOf(dataObj.get("city_name"));
                    refrence=new getSettingClass(cmp_id,cmp_name,city_name,cmp_address,tnc,
                            cmp_no,url,bussniess_status,bussniess_cat,service_at_cat);
                    JSONArray array=dataObj.getJSONArray("we_provide_service_at");

                    servises2=new String[array.length()];
                    servises_id2=new boolean[array.length()];

                    for(int i=0;i<array.length();i++)
                    {
                    JSONObject rk=array.getJSONObject(i);
                    String ser=String.valueOf(rk.get("name"));
                    String id_2=String.valueOf(rk.get("id"));
                    servises2[i]=ser;
                    servises_id2[i]=false;
                    }
                    String arr[]=service_at_cat.split(",");
                    for(int i=0;i<arr.length;i++)
                    {
                        if(!arr[i].equals("0")&&!arr[i].equals("")&&Integer.parseInt(arr[i])>0) {
                            servises_id2[Integer.parseInt(arr[i]) - 1] = true;
                        }
                        else
                        {

                        }
                        }
                    String arr2[]=bussniess_cat.split(",");
                    for(int i=0;i<arr2.length;i++)
                    {
                     for(int j=0;j<int_ids_bus.length;j++)
                     {
                         if(!arr2[i].equals("")&&int_ids_bus[j]==Integer.parseInt(arr2[i]))
                         {
                             services_id[j]=true;
                         }
                     }
                    }
                   hide_n_seek2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                           mbuilder.setTitle("Selected Type of Services");
                           mbuilder.setMultiChoiceItems(servises2, servises_id2, new DialogInterface.OnMultiChoiceClickListener() {
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
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            else
            {
                Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class fromInformation extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            super.onPreExecute();
        }

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
                JSONArray jsonArray=object.getJSONArray("data");
                servises=new String[jsonArray.length()];
                int_ids_bus=new int[jsonArray.length()];
                services_id=new boolean[jsonArray.length()];
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    //this is new generation
                    servises[i]=String.valueOf(object1.get("name"));
                    services_id[i]=false;
                    int_ids_bus[i]=Integer.parseInt(String.valueOf(object1.get("id")));
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
            hide_n_seek1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                    mbuilder.setTitle("Select days");
                    mbuilder.setMultiChoiceItems(servises,services_id, new DialogInterface.OnMultiChoiceClickListener() {
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
            //spn_city=(Spinner)findViewById(R.id.sign_up_city);
            //city=new SpinnerAdapter2(signup_Activity.this,cities);
            //spn_city.setAdapter(city);
            category=new SpinnerAdapter2(context,categories);
            spinner.setAdapter(category);
           // spn_category.setPrompt("City");
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SpinnerClass spinnerClass=categories.get(position);
                    id_city=spinnerClass.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            super.onPostExecute(s);
        }
    }
public class setServices extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/getsettingupdate";
        String data=new JsonParser().updateSettings(url,refrence.getCmp_id(),name,id_city,address,
                number,tnc,bus_cat,on_off,ser_at,convertImage,path_document);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update")
                        .setMessage(object1)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
                bus_cat="";
                ser_at="";
                on_off="";

                //make a default image and make it paste here
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e);
                Toast.makeText(context,"Some exception",Toast.LENGTH_SHORT).show();
            }
        }
              else
        {
       Toast.makeText(context,"Services are not updated",Toast.LENGTH_SHORT).show();
        }
    }
}
}
