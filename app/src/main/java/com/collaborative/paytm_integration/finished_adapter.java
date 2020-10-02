package com.collaborative.paytm_integration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class finished_adapter  extends RecyclerView.Adapter<finished_adapter.viewholder1> {
    ArrayList<appointments_data_maker> list;
    Context context;
    ProgressDialog progressDialog;
    String appointment_id;
    String status;

    public finished_adapter(ArrayList<appointments_data_maker> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public finished_adapter.viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View inflator = LayoutInflater.from(context).inflate(R.layout.recycler_appoint_basic2, parent,
                false);
        finished_adapter.viewholder1 viewhold = new finished_adapter.viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull finished_adapter.viewholder1 holder, final int position) {
        final appointments_data_maker adapter = list.get(position);
        holder.clientname.setText(adapter.getCus_name().toUpperCase());
        holder.time.setText("Time: "+adapter.getTime());
        holder.date_num.setText(adapter.getApp_date());
        holder.date_mon.setText(adapter.getApp_date_mon());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "4";
                appointment_id = adapter.getAppointment_id();
                new acceptingOrder().execute();

                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());

                // Toast.makeText(context,"Accept is clicked",Toast.LENGTH_SHORT).show();
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_id = adapter.getAppointment_id();
                Intent intent = new Intent(context, order_view.class);
                intent.putExtra("appointment_id", appointment_id);
                intent.putExtra("buttons", "allnotfine");
                intent.putExtra("4","status");
                context.startActivity(intent);
                // Toast.makeText(context,"Accept is clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder1 extends RecyclerView.ViewHolder {
        TextView date_mon, date_num;
        TextView time;
        TextView clientname;
        Button accept, view, cancel;

        public viewholder1(@NonNull View itemView) {
            super(itemView);
            date_mon = (TextView) itemView.findViewById(R.id.customer_date_mon_2);
            date_num = (TextView) itemView.findViewById(R.id.customer_date_no_2);
            time = (TextView) itemView.findViewById(R.id.customer_time_2);
            clientname = (TextView) itemView.findViewById(R.id.customer_name_2);
            accept = (Button) itemView.findViewById(R.id.accept_2);
            accept.setText("Finish");
            view = (Button) itemView.findViewById(R.id.view_2);
            //cancel=(Button)itemView.findViewById(R.id.cancel);
        }
    }

    public class acceptingOrder extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = "https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/appointmentupdate";
            String responce = new JsonParser().acceptingOrderFinally(url, appointment_id, status);
            return responce;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);
                    String object1 = String.valueOf(object.get("msg"));
                    //  Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(object1+"\nRefresh again to see the changes by clicking on latest on screen")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // new latest_Fragment().executeInanother();
                                }
                            });
                    builder.create();
                    builder.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

            } else {
                Toast.makeText(context, "No internet please go back and try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
