package com.sabanciuniv.todoapp.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.tabs.TabLayoutMediator;
import com.sabanciuniv.todoapp.R;
import com.sabanciuniv.todoapp.databinding.ActivityTrialBinding;

public class TrialActivity extends AppCompatActivity {
        private ActivityTrialBinding binding;
        String[] layout = {"todos", "notes", "done"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrialBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.viewPager2.setAdapter(new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle(), layout.length));
        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2,
                (tab, position) -> tab.setText(layout[position]));
        mediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}

