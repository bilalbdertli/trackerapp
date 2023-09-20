package com.sabanciuniv.todoapp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ToDoApi {
    @DELETE("/todoapp/todoapp/deleteNote/{noteId}")
    suspend fun deleteNote(
        @Path ("noteId") noteId: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<String>

}