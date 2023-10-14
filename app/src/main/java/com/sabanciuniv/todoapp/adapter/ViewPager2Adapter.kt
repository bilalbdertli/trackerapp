package com.sabanciuniv.todoapp.adapter

import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sabanciuniv.todoapp.activity.FragmentTodoTabs
import com.sabanciuniv.todoapp.fragment.FragmentNotesTab
import com.sabanciuniv.todoapp.model.FoodData

class ViewPager2Adapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val tabCount: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentTodoTabs("false")
            1 -> FragmentNotesTab()
            2 -> FragmentTodoTabs("true")
            else -> FragmentTodoTabs("false")
        }
    }

    override fun getItemCount(): Int {
        return tabCount
    }
}