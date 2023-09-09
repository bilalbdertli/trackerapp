package com.sabanciuniv.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.TodoRecViewAdapter
import com.sabanciuniv.todoapp.TodoRecViewAdapter.CheckListener
import com.sabanciuniv.todoapp.databinding.FragmentTodoTabsBinding
import com.sabanciuniv.todoapp.model.ToDo
import com.sabanciuniv.todoapp.repository.ToDoRepository

class FragmentTodoTabs(var isChecked: String) : Fragment() {

    private var binding: FragmentTodoTabsBinding? = null

    var repo = ToDoRepository()


    private val checkListener =
        object : CheckListener {
            override fun onButtonClicked(id: String?) {
                val app = requireActivity().application
                if (id != null) {
                    repo.changeChecked((app as ToDoApplication).srv, id)
                }
            }
        }


    var dataHandler = Handler { msg ->
        val todoList = msg.obj as List<ToDo>
        val todoRecViewAdapter = context?.let {
            TodoRecViewAdapter(
                todoList,
                it, checkListener
            )
        }
        binding!!.recViewTodos.adapter = todoRecViewAdapter
        binding!!.prgBarTodos.visibility = View.INVISIBLE
        binding!!.recViewTodos.visibility = View.VISIBLE
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTodoTabsBinding.inflate(inflater, container, false)
        val v: View = binding!!.root
        binding!!.recViewTodos.layoutManager = LinearLayoutManager(context)
        binding!!.prgBarTodos.visibility = View.VISIBLE
        binding!!.recViewTodos.visibility = View.INVISIBLE
        val app = requireActivity().application
        repo.getAllToDos((app as ToDoApplication).srv, dataHandler, isChecked)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.swipeRefresh.setOnRefreshListener {
            binding!!.prgBarTodos.visibility = View.VISIBLE
            binding!!.swipeRefresh.isRefreshing = false
            repo.getAllToDos((requireActivity().application as ToDoApplication).srv, dataHandler, isChecked)
        }
        binding!!.floatingActionButton.setOnClickListener { v ->
            val i = Intent(activity, AddNewTodo::class.java)
            startActivity(i)
        }
    }
}