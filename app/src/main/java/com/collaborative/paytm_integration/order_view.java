package com.collaborative.paytm_integration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class order_view extends AppCompatActivity {
TextView order_clicked,date_clicked,date2_clicked,name_clicked;
TextView amount_clicked,delivery_clicked,time_clicked,total_clicked,
    discount_clicked,payable_clicked,recived_clicked;
Button accept_clicked,cancel_clicked;
ImageButton back_done_right2;
RecyclerView products;
ArrayList<order_view_flager> list;
order_adapter adapter;
Toolbar toolbar;
ProgressDialog progressDialog;
Context context;
String appointment_id=null;
String status=null,status2=null;
    private String see_butons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        //toolbar=(Toolbar)findViewById(R.id.toolbar_order_view);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        context=order_view.this;
        order_clicked=(TextView)findViewById(R.id.order_clicked);
        date_clicked=(TextView)findViewById(R.id.date_clicked);
        date2_clicked=(TextView)findViewById(R.id.date2_clicked);
        name_clicked=(TextView)findViewById(R.id.name_clicked);
        amount_clicked=(TextView)findViewById(R.id.amount_clicked);
        delivery_clicked=(TextView)findViewById(R.id.delivery_clicked);
        time_clicked=(TextView)findViewById(R.id.time_clicked);
        total_clicked=(TextView)findViewById(R.id.total_clicked);
        discount_clicked=(TextView)findViewById(R.id.discount_clicked);
        payable_clicked=(TextView)findViewById(R.id.payable_clicked);
        recived_clicked=(TextView)findViewById(R.id.recieved_clicked);
        accept_clicked=(Button)findViewById(R.id.accept_clicked);
        cancel_clicked=(Button)findViewById(R.id.cancel_clicked);
        back_done_right2=(ImageButton)findViewById(R.id.back_done_right2);
        back_done_right2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        products=(RecyclerView)findViewById(R.id.bills_products);
        Intent intent=getIntent();
        appointment_id=intent.getStringExtra("appointment_id");
        see_butons=intent.getStringExtra("buttons");
        accept_clicked.setEnabled(false);
        accept_clicked.setVisibility(View.INVISIBLE);
        cancel_clicked.setEnabled(false);
        cancel_clicked.setVisibility(View.INVISIBLE);
        status2=intent.getStringExtra("status");
        //Toast.makeText(order_view.this,status2,Toast.LENGTH_SHORT).show();
        if(see_butons.equals("allfine"))
        {
            status2="2";
            accept_clicked.setEnabled(true);
            accept_clicked.setVisibility(View.VISIBLE);
            cancel_clicked.setEnabled(true);
            cancel_clicked.setVisibility(View.VISIBLE);
        }
        if(see_butons.equals("allfine2"))
        {
            status2="3";
            accept_clicked.setEnabled(true);
            accept_clicked.setVisibility(View.VISIBLE);
            accept_clicked.setText("Start");
            cancel_clicked.setEnabled(true);
            cancel_clicked.setVisibility(View.VISIBLE);
        }
        new gettingOrderMenu().execute();
        accept_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             status=status2;
         new acceptingOrder().execute();
            }
        });
        cancel_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           status="0";
           new acceptingOrder().execute();
            }
        });
    }
    public class gettingOrderMenu extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/appointmentview";
            String responce=new JsonParser().viewAppointment(url,appointment_id);
            return responce;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    JSONObject object1=object.getJSONObject("data");
                    order_clicked.setText(String.valueOf(object1.get("order_no")));
                    name_clicked.setText(String.valueOf(object1.get("customer_name")));
                    date_clicked.setText(String.valueOf(object1.get("appointment_date")));
                    amount_clicked.setText(String.valueOf(object1.get("grand_total")));
                    delivery_clicked.setText("Home/Kitchen");
                    date2_clicked.setText(String.valueOf(object1.get("book_date")));
                    time_clicked.setText(String.valueOf(object1.get("start_time"))+"-"+
                            String.valueOf(object1.get("end_time")));
                    total_clicked.setText("Total:    "+String.valueOf(object1.get("grand_total")));
                    discount_clicked.setText("Discount:     0");
                    payable_clicked.setText("Payable:   "+String.valueOf(object1.get("grand_total")));
                    recived_clicked.setText("Recieved:    0");
                    list=new ArrayList<>();
                    JSONArray array=object1.getJSONArray("product");
                    for(int i=0;i<array.length();i++)
                    {
                   JSONObject obj=array.getJSONObject(i);
                  String product_name=String.valueOf(obj.get("product_name"));
                  String category_name=String.valueOf(obj.get("category_name"));
                  String base_price=String.valueOf(obj.get("base_price"));
                  String quantity=String.valueOf(obj.get("quantity"));
                  String hsn_rate=String.valueOf(obj.get("hsn_rate"));
                  String price=String.valueOf(obj.get("price"));
                  String dis_status=String.valueOf(obj.get("dis_status"));
                  String discountVal=String.valueOf(obj.get("discountVal"));
                  String discount=String.valueOf(obj.get("discount"));
                  String product_point=String.valueOf(obj.get("product_point"));
                 list.add(new order_view_flager(quantity,product_name,category_name,
                         discountVal,discount,base_price,price,product_point));

                    }
                    adapter=new order_adapter(list,context);
                    products.setLayoutManager(new LinearLayoutManager(context));
                    products.setHasFixedSize(true);
                    products.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
            Toast.makeText(context,"No internet please go back and try again",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class acceptingOrder extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
           String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/appointmentupdate";
           String responce=new JsonParser().acceptingOrderFinally(url,appointment_id,status);
            return responce;
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(order_view.this);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();
                    accept_clicked.setEnabled(false);
                    cancel_clicked.setEnabled(false);
                    accept_clicked.setVisibility(View.INVISIBLE);
                    cancel_clicked.setVisibility(View.INVISIBLE);
          } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(order_view.this);
                    builder.setTitle("Update")
                            .setMessage("Some networking problem try again later")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    builder.show();

                    //Toast.makeText(context,"Data inconsistent check please",Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(context,"No internet please go back and try again",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
