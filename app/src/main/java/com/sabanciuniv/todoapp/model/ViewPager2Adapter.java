package com.sabanciuniv.todoapp.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private final int tabCount;

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                             int tabCount) {
        super(fragmentManager, lifecycle);
        this.tabCount = tabCount;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentTodoTabs("true");
            case 1:
                return new FragmentTodoTabs("yes");
            case 2:
                return new FragmentTodoTabs("false");
            default:
                return new FragmentTodoTabs("no");
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
