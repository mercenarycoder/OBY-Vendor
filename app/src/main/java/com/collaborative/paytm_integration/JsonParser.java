package com.collaborative.paytm_integration;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class JsonParser {
    public JsonParser()
    {

    }
    public String userLoginFromJSON(String url,String id,String pass)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try{
            Log.d("LoginData", "I reached here---> "+id);

            URL url1=new URL(url);
            httpURLConnection=(HttpURLConnection)url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            //params are made over here
            HashMap<String,String> params = new HashMap<>();
            params.put("email",id);
            params.put("password",pass);

            StringBuilder builder = new StringBuilder();
            boolean first=true;

            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String dashboardData(String url,String staff_id,String status)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("staff_id",staff_id);
            params.put("status",status);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
              //  System.out.print(current);
            }
            return current;
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
    public String getSupportInfo(String url,String staff_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("staff_id",staff_id);
            //params.put("status",status);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String saveCategory(String url,String staff_id,String name,String status,String pos)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("staff_id",staff_id);
            params.put("name",name);
            params.put("position",pos);
            params.put("status",status);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public  String getCategories(String url,String staff_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("staff_id",staff_id);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
             //   System.out.print(current);
            }
            return current;
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
    public String addServices(String url, String name, String rate,String cat_id,
                              Bitmap bitmap,String decription,String staff_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("staff_id",staff_id);
            params.put("name",name);
            params.put("category_id",cat_id);
            params.put("rate",rate);
            params.put("status","active");
            params.put("photo", String.valueOf(bitmap));
            params.put("description",decription);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String signingIn(String url,String name,String email,String password,String city_id,
                             String category_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("name",name);
            params.put("email",email);
            params.put("contact_number",password);
            params.put("category",category_id);
            params.put("city", city_id);
            //params.put("description",decription);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some exception is getting raised over here");
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String viewAppointment(String url,String appointment_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("appointment_id",appointment_id);
            //params.put("description",decription);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
               // System.out.print(current);
            }
            return current;
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
    public String acceptingOrderFinally(String url,String appointment_id,String status)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("appointment_id",appointment_id);
            params.put("status",status);
            //params.put("description",decription);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String changingProduct(String url,String staff_id,String service_id,String status)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("service_id",service_id);
            params.put("staff_id",staff_id);
            params.put("status",status);
            //params.put("description",decription);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String updateSlots(String url,String slot_id,String day_name,String start_time,String end_time,
                           String status,String max_appointment)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            //params.put("service_id",service_id);
            params.put("slot_id",slot_id);
            params.put("day_name",day_name);
            params.put("start_time",start_time);
            params.put("end_time",end_time);
            //params.put("day_repeate",day_repeate);
            params.put("max_appointment",max_appointment);
            params.put("status",status);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public  String getServices(String url,String product_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            //params.put("service_id",service_id);
            params.put("category_id",product_id);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String updateSettings(String url,String cmp_id,String name,String cmp_city,String address,
                                 String number,String tnc,
                                 String bus_cat,String bus_status,String service_id,String img,
                                 String doc)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {

            URL url2 = new URL(url);

            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("cmp_id",cmp_id);
            params.put("cmp_name",name);
            params.put("city_name",cmp_city);
            params.put("address",address);
            params.put("term_condition",tnc);
            params.put("contact_no",number);
            //img.compress()
            params.put("logo",img);
            params.put("business_doc_upload",doc);
            params.put("business_status",bus_status);
            params.put("business_category",bus_cat);
            params.put("service_at",service_id);

            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String updateServices(String url,String prod_id,String name,String rate,String description,
                                 String image,String hsn_id,String cat_id,String status)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {

            URL url2 = new URL(url);

            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("product_id",prod_id);
            params.put("name",name);
            params.put("category_id",cat_id);
            params.put("rate",rate);
            params.put("status",status);
            params.put("description",description);
            //img.compress()
            params.put("photo",image);
            params.put("hsn_id",hsn_id);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String getProduct(String url,String prod_id)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {

            URL url2 = new URL(url);

            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("product_id",prod_id);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
    public String updateCategory(String url,String category_id,String name,String position,String status)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {

            URL url2 = new URL(url);

            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("category_id",category_id);
            params.put("name",name);
            params.put("position",position);
            params.put("status",status);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
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
}
