package com.sabanciuniv.todoapp.repository

import android.os.Handler
import android.os.Message
import android.util.Log
import com.sabanciuniv.todoapp.model.Note
import com.sabanciuniv.todoapp.model.ToDo
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.ExecutorService

class ToDoRepository {
    private val host = "https://todoapp-1-c1be29230266.herokuapp.com"
    fun getAllToDos(srv: ExecutorService, uiHandler: Handler) {
        val retVal: MutableList<ToDo> = ArrayList()
        srv.execute {
            try {
                val url = URL("$host/todoapp/todoapp/getAll")
                val connection = url.openConnection() as HttpURLConnection
                val reader =
                    BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = ""
                val stringBuilder = StringBuilder()
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                val jsonArray = JSONArray(stringBuilder.toString())
                for (i in 0 until jsonArray.length()) {
                    val currentToDo = jsonArray.getJSONObject(i)
                    Log.i("DEV", currentToDo.getString("dueDate"))
                    Log.i("DEVTRIAL", currentToDo.toString())
                    retVal.add(
                        ToDo(
                            currentToDo.getString("description"),
                            currentToDo.getString("title"),
                            currentToDo.getString("dueDate"),
                            currentToDo.getBoolean("checked"),
                            currentToDo.getString("id")
                        )
                    )
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val msgErr = Message()
            msgErr.obj = retVal
            uiHandler.sendMessage(msgErr)
        }
    }

    fun addToDo(
        srv: ExecutorService,
        handler: Handler,
        title: String?,
        toDo: String?,
        dueDate: String?
    ) {
        val obj = JSONObject()
        try {
            obj.put("description", toDo)
            obj.put("title", title)
            obj.put("dueDate", dueDate)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        srv.execute {
            try {
                val url = URL("$host/todoapp/todoapp/addToDo")
                val conn =
                    url.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.doOutput = true
                conn.requestMethod = "POST"
                conn.setRequestProperty("content-type", "application/json")
                val writer =
                    BufferedOutputStream(conn.outputStream)
                writer.write(obj.toString().toByteArray())
                writer.flush()
                val reader =
                    BufferedReader(InputStreamReader(conn.inputStream))
                val buffer = StringBuilder()
                var line: String? = ""
                while (reader.readLine().also { line = it } != null) {
                    buffer.append(line)
                }
                handler.sendEmptyMessage(0)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun changeChecked(srv: ExecutorService, id: String) {
        srv.execute {
            try {
                val url = URL("$host/todoapp/todoapp/updateToDo/$id")
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.doOutput = true
                connection.requestMethod = "PUT"
                val reader =
                    BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = ""
                val stringBuilder = StringBuilder()
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                val reply = stringBuilder.toString()
                Log.i("RESPONSE", reply)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            /*
                    Message msgErr = new Message();
                    msgErr.obj = retVal;
                    uiHandler.sendMessage(msgErr);
                    */
        }
    }

    fun deleteToDoById(srv: ExecutorService, id: String, uiHandler: Handler) {
        srv.execute {
            var reply = "Could not connect to the services, please try again later."
            try {
                val url = URL("$host/todoapp/todoapp/deleteToDo/$id")
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.doOutput = true
                connection.requestMethod = "DELETE"
                val reader =
                    BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = ""
                val stringBuilder = StringBuilder()
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                reply = stringBuilder.toString()
                Log.i("RESPONSE TO DELETION", reply)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val msgErr = Message()
            msgErr.obj = reply
            uiHandler.sendMessage(msgErr)
        }
    }

    fun getAllNotes(srv: ExecutorService, uiHandler: Handler) {
        val retVal: MutableList<Note> = ArrayList()
        srv.execute {
            try {
                val url = URL("$host/todoapp/todoapp/getAllNotes")
                val connection = url.openConnection() as HttpURLConnection
                val reader =
                    BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = ""
                val stringBuilder = StringBuilder()
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                val jsonArray = JSONArray(stringBuilder.toString())
                for (i in 0 until jsonArray.length()) {
                    val currentNote = jsonArray.getJSONObject(i)
                    retVal.add(
                        Note(
                            currentNote.getString("description"),
                            currentNote.getString("title"),
                            currentNote.getString("dueDate"),
                            currentNote.getString("id")
                        )
                    )
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val msgErr = Message()
            msgErr.obj = retVal
            uiHandler.sendMessage(msgErr)
        }
    }
}