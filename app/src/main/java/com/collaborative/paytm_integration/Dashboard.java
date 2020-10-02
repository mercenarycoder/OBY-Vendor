package com.collaborative.paytm_integration;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
// androidx.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
private DrawerLayout drawerLayout;
private NavigationView navigationView;
private LinearLayout appointments;
private LinearLayout dashboard,support,services,login,setting,slots_info_get;
Toolbar toolbar;
private TextView toolname;
FrameLayout frameLayout;
private ActionBarDrawerToggle actionBarDrawerToggle;
int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpToolbar();
    navigationView=(NavigationView)findViewById(R.id.navigation_view_MainActivity);
    appointments=(LinearLayout)findViewById(R.id.appointments);
    toolname=(TextView)findViewById(R.id.menu);
    frameLayout=(FrameLayout)findViewById(R.id.frame_base);
    dashboard=(LinearLayout)findViewById(R.id.dashboard);
    support=(LinearLayout)findViewById(R.id.support);
    services=(LinearLayout)findViewById(R.id.setting);
    setting=(LinearLayout)findViewById(R.id.services);
    login=(LinearLayout)findViewById(R.id.logout);
    slots_info_get=(LinearLayout)findViewById(R.id.slots_info_get);
   //this is just a redundant code these 3 lines
    Intent intent=getIntent();
    String result=intent.getStringExtra("json");
    //setExtras(result);
   //from here all usefull codes
      appointments.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //appointments.setBackgroundColor(Color.parseColor("#beie2d"));
     // Toast.makeText(Dashboard.this,"i am clicked",Toast.LENGTH_LONG).show();
      toolname.setText("Appointments");
      getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Appointment_fragment()).commit();
      drawerLayout.closeDrawers();
      count=0;
              //appointments.setBackgroundColor(Color.parseColor("#FFC533"));
          }
      });

      getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Dashboard_Fragment()).commit();
      dashboard.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
     getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Dashboard_Fragment()).commit();
     toolname.setText("DashBoard");
     drawerLayout.closeDrawers();
       count=0;
          }
      });
      support.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
      toolname.setText("Support");
      getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Support_fragment()).commit();
      drawerLayout.closeDrawers();
              count=0;
          }
      });
        slots_info_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolname.setText("Slots");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Slots_Fragment()).commit();
                drawerLayout.closeDrawers();
                count=0;
            }
        });
      services.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
      toolname.setText("Setting");
      getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Services_Fragement()).commit();
      drawerLayout.closeDrawers();
              count=0;
          }
      });
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              SharedPreferences p=getApplicationContext().getSharedPreferences("login_details",
                      getApplicationContext().MODE_PRIVATE);
              SharedPreferences.Editor editor=p.edit();
              editor.clear();
              editor.apply();
              startActivity(new Intent(Dashboard.this,MainActivity.class));
              finish();
          }
      });
      setting.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
      toolname.setText("Services");
      getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Setting_Fragment()).commit();
      drawerLayout.closeDrawers();
              count=0;
          }
      });
    }

    private void setExtras(String result)
    {
        try {
            JSONObject object=new JSONObject(result);
            JSONObject jsonObject1=object.getJSONObject("data");
            SharedPreferences p=getApplicationContext().getSharedPreferences("login_details",
                    getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor=p.edit();
            System.out.println( String.valueOf(jsonObject1.get("auth_key")));
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
            Toast.makeText(Dashboard.this,p.getString("auth_key","Hare Krishna")
                    ,Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e+" soo this is the exception");
            Toast.makeText(Dashboard.this,"Some Error",Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpToolbar() {
 drawerLayout=(DrawerLayout)findViewById(R.id.draw_layout_main);
 toolbar=findViewById(R.id.toolbarmain);
setSupportActionBar(toolbar);
actionBarDrawerToggle=new ActionBarDrawerToggle
        (this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().
                setColor(getResources().getColor(R.color.colorDrawer));
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(count==0)
        {
            Toast.makeText(Dashboard.this, "click again to exit", Toast.LENGTH_SHORT).show();
        count++;
        return;
        }
        super.onBackPressed();
    }
}
