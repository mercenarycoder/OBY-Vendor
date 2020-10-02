package com.collaborative.paytm_integration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class previous_Fragment extends Fragment {
RecyclerView finished_recycle;
RecyclerView cancel_recycle;
previous_adapter adapter;
previous_adapter adapter2;
ArrayList<appointments_data_maker> list;
ArrayList<appointments_data_maker> list2;
Context recyclerContext;
Button latest,previous;
ProgressBar p_finished,p_cancelled;
TextView t_finished,t_cancelled;
ProgressDialog progressDialog;
fragmentAsync async1;
fragmentAsync2 async2;
@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       // list=new ArrayList<>();
        //list2=new ArrayList<>();
        recyclerContext=this.getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.previous_appointment, container, false);
    latest=getActivity().findViewById(R.id.latest);
    previous=getActivity().findViewById(R.id.previous);
    p_finished=(ProgressBar)view.findViewById(R.id.loader_finished);
    p_cancelled=(ProgressBar)view.findViewById(R.id.loader_cancel);
    t_finished=(TextView)view.findViewById(R.id.showwhen_not_finished);
    t_cancelled=(TextView)view.findViewById(R.id.showwhen_not_cancel);
    p_cancelled.setVisibility(View.VISIBLE);
    p_finished.setVisibility(View.VISIBLE);
    finished_recycle=(RecyclerView)view.findViewById(R.id.finished_recycler);
    cancel_recycle=(RecyclerView)view.findViewById(R.id.cancel_recycler);
    async1=new fragmentAsync();
    async2=new fragmentAsync2();
    try {
    async1.execute();
    async2.execute();
}
catch (Exception e)
{
    System.out.println(e);
}
        return view;
    }

    @Override
    public void onPause() {
    if(async1.getStatus()==AsyncTask.Status.RUNNING)
    {
        async1.cancel(true);
    }
        if(async2.getStatus()==AsyncTask.Status.RUNNING)
        {
            async2.cancel(true);
        }
    super.onPause();
    }

    private  class fragmentAsync2 extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
          /*  progressDialog=new ProgressDialog(recyclerContext);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         */   // progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/listofappointment";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.dashboardData(url,staff_id,"0");

            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            p_cancelled.setVisibility(View.INVISIBLE);
            if(s!=null)
            {
                try {
                    HashMap<String, String> month_teller = new HashMap<>();
                    month_teller.put("01", "Jan");
                    month_teller.put("02", "Feb");
                    month_teller.put("03", "Mar");
                    month_teller.put("04", "Apr");
                    month_teller.put("05", "May");
                    month_teller.put("06", "Jun");
                    month_teller.put("07", "Jul");
                    month_teller.put("08", "Aug");
                    month_teller.put("09", "Sep");
                    month_teller.put("10", "Oct");
                    month_teller.put("11", "Nov");
                    month_teller.put("12", "Dec");
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList name = new ArrayList<String>();
                    if(jsonArray.length()==0)
                    {
                        t_cancelled.setVisibility(View.VISIBLE);
                        return;
                    }
                    list2=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String cus_name = String.valueOf(object.get("customer_name"));
                        String cus_mobile = String.valueOf(object.get("customer_mobile"));
                        String appointment_id = String.valueOf(object.get("appointment_id"));
                        String s_id = String.valueOf(object.get("s_id"));
                        String payment_mode = String.valueOf(object.get("payment_mode"));
                        String order_no = String.valueOf(object.get("order_no"));
                        String grand_total = String.valueOf(object.get("grand_total"));
                        String book_date = String.valueOf(object.get("book_date"));
                        String time = String.valueOf(object.get("start_time")) + " to " +
                                String.valueOf(object.get("end_time"));
                        String slice[] = String.valueOf(object.get("appointment_date")).split("-");
                        String app_date = slice[0];
                        String app_date_mon = month_teller.get(slice[1]);
                        String day = String.valueOf(object.get("day"));
                        String pay_status = String.valueOf(object.get("payment_status"));
                        String book_time = String.valueOf(object.get("book_time"));
                        System.out.println(cus_name);
                        list2.add(new appointments_data_maker(payment_mode, appointment_id, s_id, book_time, cus_name, cus_mobile,
                                book_date, app_date, app_date_mon, pay_status, order_no, grand_total, day, time));
                    }
                    adapter2 = new previous_adapter(list2, recyclerContext);
                    cancel_recycle.setHasFixedSize(true);
                    cancel_recycle.setLayoutManager(new LinearLayoutManager(recyclerContext));
                    cancel_recycle.setAdapter(adapter2);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    //JSONObject jsonObject=new JSONObject(s);
                    t_cancelled.setVisibility(View.VISIBLE);
                    Toast.makeText(recyclerContext,"No Finished data",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //list.add(new dashboard_Adapter("Client Name","Time:10:00 to 12:00",
                //      "23","jan"));
                Toast.makeText(getContext(),"No Internet connection",Toast.LENGTH_SHORT).show();
            }
            previous.setEnabled(true);
            latest.setEnabled(true);
           // previous.setPressed(true);
            //latest.setPressed(false);
        }
    }
    private  class fragmentAsync extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
           progressDialog=new ProgressDialog(recyclerContext);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

          progressDialog.show();
            previous.setBackgroundResource(R.drawable.custom_tab3);
            latest.setBackgroundResource(R.drawable.appointment_custom_tabs);
            latest.setEnabled(false);
            previous.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {

            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/listofappointment";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.dashboardData(url,staff_id,"4");

            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            p_finished.setVisibility(View.INVISIBLE);
            if(s!=null)
            {
                try {
                    HashMap<String,String> month_teller=new HashMap<>();
                    month_teller.put("01","Jan");
                    month_teller.put("02","Feb");
                    month_teller.put("03","Mar");
                    month_teller.put("04","Apr");
                    month_teller.put("05","May");
                    month_teller.put("06","Jun");
                    month_teller.put("07","Jul");
                    month_teller.put("08","Aug");
                    month_teller.put("09","Sep");
                    month_teller.put("10","Oct");
                    month_teller.put("11","Nov");
                    month_teller.put("12","Dec");
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList name = new ArrayList<String>();
                    if(jsonArray.length()==0)
                    {
                        t_finished.setVisibility(View.VISIBLE);
                        return;
                    }
                    list=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String cus_name=String.valueOf(object.get("customer_name"));
                        String cus_mobile=String.valueOf(object.get("customer_mobile"));
                        String appointment_id=String.valueOf(object.get("appointment_id"));
                        String s_id=String.valueOf(object.get("s_id"));
                        String payment_mode=String.valueOf(object.get("payment_mode"));
                        String order_no=String.valueOf(object.get("order_no"));
                        String grand_total=String.valueOf(object.get("grand_total"));
                        String book_date=String.valueOf(object.get("book_date"));
                        String time=String.valueOf(object.get("start_time"))+" to "+
                                String.valueOf(object.get("end_time"));
                        String slice[]=String.valueOf(object.get("appointment_date")).split("-");
                        String app_date=slice[0];
                        String app_date_mon=month_teller.get(slice[1]);
                        String day=String.valueOf(object.get("day"));
                        String pay_status=String.valueOf(object.get("payment_status"));
                        String book_time=String.valueOf(object.get("book_time"));
                        System.out.println(cus_name);
                        list.add(new appointments_data_maker(payment_mode,appointment_id,s_id,book_time,cus_name,cus_mobile,
                                book_date,app_date,app_date_mon,pay_status,order_no,grand_total,day,time));
                    }
                    finished_recycle.setHasFixedSize(true);
                    finished_recycle.setLayoutManager(new LinearLayoutManager(recyclerContext));
                    adapter=new previous_adapter(list,recyclerContext);
                    finished_recycle.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    t_finished.setVisibility(View.VISIBLE);
                    Toast.makeText(recyclerContext,"No cancelled data",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //list.add(new dashboard_Adapter("Client Name","Time:10:00 to 12:00",
                //      "23","jan"));
                Toast.makeText(getContext(),"No appointments right now",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
