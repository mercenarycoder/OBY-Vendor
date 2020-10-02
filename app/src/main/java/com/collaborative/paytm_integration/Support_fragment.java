package com.collaborative.paytm_integration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Support_fragment extends Fragment {
    TextView email,number;
    ProgressDialog progressDialog;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    View  view=inflater.inflate(R.layout.support,container,false);
    email=(TextView)view.findViewById(R.id.support_email123);
    number=(TextView)view.findViewById(R.id.support_mobile123);
    context=this.getActivity();
    new getallsupport().execute();
    return view;
    }
    public class getallsupport extends AsyncTask<String,String,String>
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
        protected String doInBackground(String...strings) {
          String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/getsupport";
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().
                            getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            JsonParser parser=new JsonParser();
            String responce=parser.getSupportInfo(url,staff_id);
            return responce;
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
                    email.setText(String.valueOf(object1.get("email")));
                    number.setText(String.valueOf(object1.get("mobile_no")));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Something going wrong",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {

            }
        }
    }
}
