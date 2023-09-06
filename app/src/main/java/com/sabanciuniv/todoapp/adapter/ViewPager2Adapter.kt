package com.sabanciuniv.todoapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sabanciuniv.todoapp.activity.FragmentTodoTabs

class ViewPager2Adapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val tabCount: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentTodoTabs("true")
            1 -> FragmentTodoTabs("yes")
            2 -> FragmentTodoTabs("false")
            else -> FragmentTodoTabs("no")
        }
    }

    override fun getItemCount(): Int {
        return tabCount
    }
}