package com.sabanciuniv.todoapp.activity

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.TodoRecViewAdapter
import com.sabanciuniv.todoapp.databinding.FragmentTodoTabsBinding
import com.sabanciuniv.todoapp.`interface`.RecyclerViewInterface
import com.sabanciuniv.todoapp.model.ToDo
import com.sabanciuniv.todoapp.repository.ToDoRepository

class FragmentTodoTabs(var isChecked: String) : Fragment(), RecyclerViewInterface {

    private var binding: FragmentTodoTabsBinding? = null

    var repo = ToDoRepository()

    lateinit var todoList: MutableList<ToDo>
    lateinit var todoRecViewAdapter : TodoRecViewAdapter





    var dataHandler = Handler { msg ->
        todoList = msg.obj as MutableList<ToDo>
         todoRecViewAdapter = context?.let {
             TodoRecViewAdapter(
                 todoList,
                 it, this
             )
         }!!
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
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoList.removeAt(viewHolder.adapterPosition)
                todoRecViewAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
        helper.attachToRecyclerView(binding!!.recViewTodos)
        binding!!.swipeRefresh.setOnRefreshListener {
            binding!!.prgBarTodos.visibility = View.VISIBLE
            binding!!.swipeRefresh.isRefreshing = false
            repo.getAllToDos((requireActivity().application as ToDoApplication).srv, dataHandler, isChecked)
        }
    }

    override fun onCheckboxClicked(position: Int, id: String) {
        val app = requireActivity().application
        todoList.removeAt(position)
        todoRecViewAdapter.notifyItemRemoved(position)
        repo.changeChecked((app as ToDoApplication).srv, id)
    }


}