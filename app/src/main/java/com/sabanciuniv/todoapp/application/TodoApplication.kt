package com.sabanciuniv.todoapp

import android.app.Application
import java.util.concurrent.Executors

class ToDoApplication : Application() {
    public var srv = Executors.newCachedThreadPool()
}