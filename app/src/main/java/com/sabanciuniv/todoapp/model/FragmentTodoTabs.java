package com.sabanciuniv.todoapp.model;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sabanciuniv.todoapp.AddNewTodo;
import com.sabanciuniv.todoapp.MainActivity;
import com.sabanciuniv.todoapp.R;
import com.sabanciuniv.todoapp.ToDoApplication;
import com.sabanciuniv.todoapp.TodoRecViewAdapter;
import com.sabanciuniv.todoapp.databinding.FragmentTodoTabsBinding;

import java.util.List;


public class FragmentTodoTabs extends Fragment  {

    private FragmentTodoTabsBinding binding;

    ToDoRepository repo = new ToDoRepository();

    String isChecked;
    public FragmentTodoTabs(String isChecked) {
        this.isChecked = isChecked;
    }

    private final TodoRecViewAdapter.CheckListener checkListener = new TodoRecViewAdapter.CheckListener() {
        @Override
        public void onButtonClicked(String id) {
            Application app = requireActivity().getApplication();
            repo.changeChecked(((ToDoApplication) app).srv, id);
        }
    };
    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<ToDo> todoList = (List<ToDo>) msg.obj;
            TodoRecViewAdapter todoRecViewAdapter = new TodoRecViewAdapter(todoList, getContext(), checkListener);
            binding.recViewTodos.setAdapter(todoRecViewAdapter);
            binding.prgBarTodos.setVisibility(View.INVISIBLE);
            binding.recViewTodos.setVisibility(View.VISIBLE);
            return true;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTodoTabsBinding.inflate(inflater, container, false);
        View v =  binding.getRoot();
        binding.recViewTodos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.prgBarTodos.setVisibility(View.VISIBLE);
        binding.recViewTodos.setVisibility(View.INVISIBLE);
        Application app = requireActivity().getApplication();
        repo.getAllToDos(((ToDoApplication) app).srv,dataHandler);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.prgBarTodos.setVisibility(View.VISIBLE);
                binding.swipeRefresh.setRefreshing(false);
                repo.getAllToDos(((ToDoApplication)requireActivity().getApplication()).srv,dataHandler);
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AddNewTodo.class);
            startActivity(i);
        });
    }
}