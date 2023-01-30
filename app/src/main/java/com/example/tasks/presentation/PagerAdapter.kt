package com.example.tasks.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tasks.presentation.ui.AddNewListFragment
import com.example.tasks.presentation.ui.MainFragment
import com.example.tasks.presentation.ui.testFragment

class PagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> testFragment()
            else -> testFragment()
        }
    }
}