package com.sabanciuniv.todoapp.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sabanciuniv.todoapp.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ToDoRepository {

    public void getAllToDos(ExecutorService srv, Handler uiHandler){
        List<ToDo> retVal =new ArrayList<>();

        srv.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://172.18.96.1:8080/todoapp/todoapp/getAll");
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
                        retVal.add(new ToDo(currentToDo.getString("description"), currentToDo.getString("title"), currentToDo.getString("dueDate")));

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



}
