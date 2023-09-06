package com.sabanciuniv.todoapp.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.adapter.ViewPager2Adapter
import com.sabanciuniv.todoapp.databinding.ActivityTrialBinding

class TrialActivity : AppCompatActivity() {
    private var binding: ActivityTrialBinding? = null
    var layout = arrayOf("todos", "notes", "done")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrialBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.viewPager2.adapter =
            ViewPager2Adapter(supportFragmentManager, lifecycle, layout.size)
        val mediator = TabLayoutMediator(
            binding!!.tabLayout, binding!!.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = layout[position]
        }
        mediator.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}