package com.sabanciuniv.todoapp.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.databinding.ActivityMainBinding
import com.sabanciuniv.todoapp.adapter.ViewPager2Adapter

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var layout = arrayOf("TODOS", "NOTES", "DONE")
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
    } /*
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