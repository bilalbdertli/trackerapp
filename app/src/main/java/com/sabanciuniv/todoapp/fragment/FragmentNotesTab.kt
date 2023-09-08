package com.sabanciuniv.todoapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanciuniv.todoapp.NoteRecViewAdapter
import com.sabanciuniv.todoapp.activity.AddNewTodo
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.TodoRecViewAdapter
import com.sabanciuniv.todoapp.activity.AddNewNote
import com.sabanciuniv.todoapp.databinding.FragmentNotesTabBinding
import com.sabanciuniv.todoapp.databinding.FragmentTodoTabsBinding
import com.sabanciuniv.todoapp.model.Note
import com.sabanciuniv.todoapp.model.ToDo
import com.sabanciuniv.todoapp.repository.ToDoRepository

class FragmentNotesTab : Fragment() {

    private var binding: FragmentNotesTabBinding? = null

    var repo = ToDoRepository()


    var dataHandler = Handler { msg ->
        val noteList = msg.obj as List<Note>
        val noteRecViewAdapter = context?.let {
            NoteRecViewAdapter(
                noteList,
                it
            )
        }
        binding!!.recViewTodos.adapter = noteRecViewAdapter
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
        binding = FragmentNotesTabBinding.inflate(inflater, container, false)
        val v: View = binding!!.root
        binding!!.recViewTodos.layoutManager = LinearLayoutManager(context)
        binding!!.prgBarTodos.visibility = View.VISIBLE
        binding!!.recViewTodos.visibility = View.INVISIBLE
        val app = requireActivity().application
        repo.getAllNotes((app as ToDoApplication).srv, dataHandler)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.swipeRefresh.setOnRefreshListener {
            binding!!.prgBarTodos.visibility = View.VISIBLE
            binding!!.swipeRefresh.isRefreshing = false
            repo.getAllNotes((requireActivity().application as ToDoApplication).srv, dataHandler)
        }
        binding!!.floatingActionButton.setOnClickListener { v ->
            val i = Intent(activity, AddNewNote::class.java)
            startActivity(i)
        }
    }


}