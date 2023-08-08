package com.sabanciuniv.todoapp.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sabanciuniv.todoapp.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ToDoRepository {

    public void getAllToDos(ExecutorService srv, Handler uiHandler){
        List<ToDo> retVal =new ArrayList<>();

        srv.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.7:8080/todoapp/todoapp/getAll");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject currentToDo = jsonArray.getJSONObject(i);
                        Log.i("DEV", currentToDo.getString("dueDate"));
                        Log.i("DEVTRIAL", currentToDo.toString());

                        retVal.add(new ToDo(currentToDo.getString("description"), currentToDo.getString("title"), currentToDo.getString("dueDate"), currentToDo.getBoolean("checked"), currentToDo.getString("id")));

                    }
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


                Message msgErr = new Message();
                msgErr.obj = retVal;
                uiHandler.sendMessage(msgErr);

            }
        });
    }

    public void addToDo(ExecutorService srv, Handler handler, String  title,String toDo, String dueDate){

        JSONObject obj = new JSONObject();
        try {
            obj.put("description",toDo);
            obj.put("title",title);
            obj.put("dueDate",dueDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        srv.execute(()->{

            try {
                URL url = new URL("http://192.168.1.7:8080/todoapp/todoapp/addToDo");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("content-type","application/json");

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(obj.toString().getBytes());
                writer.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                handler.sendEmptyMessage(0);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });









    }

    public void changeChecked(ExecutorService srv, String id){

        srv.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.7:8080/todoapp/todoapp/updateToDo/" + id );
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String reply = (stringBuilder.toString());
                    Log.i("RESPONSE", reply);


                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                /*
                Message msgErr = new Message();
                msgErr.obj = retVal;
                uiHandler.sendMessage(msgErr);
                */


            }
        });
    }




}
