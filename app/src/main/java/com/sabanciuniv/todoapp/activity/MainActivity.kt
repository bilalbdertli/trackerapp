package com.sabanciuniv.todoapp.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.databinding.ActivityMainBinding
import com.sabanciuniv.todoapp.adapter.ViewPager2Adapter
import com.sabanciuniv.todoapp.dialog.ChartDialog
import com.sabanciuniv.todoapp.dialog.CustomDialog
import com.sabanciuniv.todoapp.dialog.GoalPickerDialog
import com.sabanciuniv.todoapp.`interface`.ResetDailyList
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.FoodDataSerializer
import com.sabanciuniv.todoapp.model.RecentDayData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


val Context.dataStore by dataStore("food-data.json", FoodDataSerializer)
class MainActivity : AppCompatActivity(), ResetDailyList {
    private var binding: ActivityMainBinding? = null
    private val layout = arrayOf("TODOS", "NOTES", "DONE")
    private var calories: Int = 2000
    private var earnedCals: Int = 0
    private var foodItems: MutableList<Food> = mutableListOf()
    private val formatter = DateTimeFormatter.ofPattern("dd MMM, E")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.title = "TrackerApp"
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

        lifecycleScope.launch {
            initialize()
        }

        binding!!.floatingActionButton.setOnClickListener {
            val i = Intent(this, AddNewTodo::class.java)
            startActivity(i)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dialogCalOpen -> {
                lifecycleScope.launch {
                    initialize()
                    showDialog()
                }
            }
            R.id.dialogSetCal -> {
                lifecycleScope.launch {
                    calories = getCalories()
                    showCaloryPicker()
                }

            }
            R.id.dialogChart -> {
                showChartDialog()
            }
        }

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

    private fun showCaloryPicker(){
        val dialog = GoalPickerDialog(this, calories, this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    private fun showDialog(){
        val dialog = CustomDialog(this, foodItems, calories, earnedCals, this, this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    private fun showChartDialog(){
        val dialog = ChartDialog(this, mutableListOf(RecentDayData("16 Sep, Mon",2200,3031),
            RecentDayData("17 Sep, Tue",2000,2016),RecentDayData("18 Sep, Wed",2300,2659),
            RecentDayData("19 Sep, Thu",1900,1752),RecentDayData("20 Sep, Fri",1800,2451),
            RecentDayData("21 Sep, Sat",2200,2751),RecentDayData("22 Sep, Sun",2200,1956))
            , 1000 )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private suspend fun getDate(): String{
        return dataStore.data.first().currentDay
    }
    private suspend fun initialize(){
        if(LocalDate.now().format(formatter) != getDate()){
            updateDate()
        }
        calories = getCalories()
        foodItems = getFoodList()
        earnedCals = getConsumedCals()
    }
    private suspend fun setCalories(t: Int){
        dataStore.updateData {
            it.copy(
                calories = t
            )
        }
    }


    private suspend fun getCalories(): Int{
        return dataStore.data.first().calories
    }
    private suspend fun getConsumedCals(): Int{
        return dataStore.data.first().consumed
    }
    private suspend fun deleteDailyList(){
        dataStore.updateData {
            it.copy(
                foodList = mutableListOf(),
                consumed = 0
            )
        }
    }

    private suspend fun updateDate(){
        dataStore.updateData {
            it.copy(
                foodList = mutableListOf(),
                currentDay = LocalDate.now().format(formatter)
            )
        }
    }

    private suspend fun addList(name: String, cal: Int) {
        dataStore.updateData {
            val newItem = Food(name,cal)
            val updatedCurrentDay = LocalDate.now().format(formatter)
            val updatedFoodList: MutableList<Food>
            val newConsumed: Int
            if (it.currentDay != updatedCurrentDay) {
                // If the currentDay doesn't match the current date, replace with the new item
                updatedFoodList = mutableListOf<Food>(newItem)
                newConsumed = 0
            } else {
                // If currentDay matches the current date, append the new item
                val newFoodList: MutableList<Food> = it.foodList.toMutableList()
                newFoodList.add(newItem)
                updatedFoodList = newFoodList
                newConsumed = it.consumed + cal
            }

            it.copy(foodList = updatedFoodList, currentDay = updatedCurrentDay, consumed = newConsumed)
        }
    }


    override suspend fun onResetClicked() {
        deleteDailyList()
    }

    override suspend fun onAddClicked(name: String, cal: Int) {
        addList(name, cal)
    }

    override suspend fun onGoalChanged(newGoal: Int) {
        setCalories(newGoal)
    }
    private suspend fun getFoodList(): MutableList<Food>{
        return dataStore.data.first().foodList
    }

    /*
    suspend fun trialAddToList(){
        dataStore.updateData {
            val newFoodList: MutableList<Food> = it.foodList.toMutableList()
            newFoodList.add(Food("test", 200))
            it.copy(foodList = newFoodList)
        }
    }
    private suspend fun addFoodItem() {
        addList("exampleFood", 500)
        initialize()
    }
    private fun parseFoodListFromString(foodListAsString: String): MutableList<Food> {
        val items = foodListAsString.split(":")
        val foodList = mutableListOf<Food>()

        for (item in items) {
            val parts = item.split(",")
            if (parts.size == 2) {
                val name = parts[0]
                val calories = parts[1].toIntOrNull()

                if (calories != null) {
                    foodList.add(Food(name, calories))
                }
            }
        }

        return foodList
    }
    private suspend fun changeDailyGoal(t:Int){
        setCalories(t)
        calories = getCalories()

    }

   private suspend fun getFoodItems(): MutableList<Food>{
       val foodData = dataStore.data.first()
       return parseFoodListFromString(foodData.food)
   }

    private suspend fun getAllFoods(): String{
        return dataStore.data.first().food

    }
    private fun calculateTotalCalories(foodList: MutableList<Food>): Int {
        return foodList.sumOf { it.calories }
    }

    */


}