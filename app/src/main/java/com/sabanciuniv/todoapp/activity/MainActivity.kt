package com.sabanciuniv.todoapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.databinding.ActivityMainBinding
import com.sabanciuniv.todoapp.adapter.ViewPager2Adapter
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.FoodData
import com.sabanciuniv.todoapp.model.FoodDataSerializer
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("food-data.json", FoodDataSerializer)
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var layout = arrayOf("CALORIES", "TODOS", "NOTES", "DONE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.title = "To-Do's of the Day"
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        window.statusBarColor = SurfaceColors.SURFACE_2.getColor(this)
        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)
        binding!!.tabLayout.setBackgroundColor(SurfaceColors.SURFACE_2.getColor(this))
        binding!!.viewPager2.adapter =
            ViewPager2Adapter(supportFragmentManager, lifecycle, layout.size, dataStore)
        val mediator = TabLayoutMediator(
            binding!!.tabLayout, binding!!.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = layout[position]
        }
        mediator.attach()

        binding!!.floatingActionButton.setOnClickListener { v ->
            val i = Intent(this, AddNewTodo::class.java)
            startActivity(i)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    /*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToDo newToDo = intent.getSerializableExtra("newTodo", ToDo.class);
        todoList.add(newToDo);
        binding.recTodo.getAdapter().notifyItemInserted(todoList.size()-1);
    }*/
    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==R.id.mnPostComment){
            //to post activity
            Intent i = new Intent(this, AddNewTodo.class);
            startActivity(i);

        }
        return true;
    }*/
    /*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }*/

}