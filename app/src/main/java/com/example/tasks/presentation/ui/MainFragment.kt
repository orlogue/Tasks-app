package com.example.tasks.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.tasks.R
import com.example.tasks.databinding.FragmentMainBinding
import com.example.tasks.presentation.MainViewModel
import com.example.tasks.presentation.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        initializePager()
        addListButtonListener()
        return binding.root
    }

    private fun initializePager() {
        binding.apply {
            pager.adapter = PagerAdapter(requireActivity())
            TabLayoutMediator(tabLayout, pager) { tab, pos ->
                when (pos) {
                    0 -> {
                        tab.setIcon(R.drawable.star_24)
                    }
                    1 -> {
                        tab.setText(R.string.default_list)
                    }
                }
            }.attach()

            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    context?.let { tab?.icon?.setTint(ContextCompat.getColor(it, R.color.blue)) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    context?.let { tab?.icon?.setTint(ContextCompat.getColor(it, R.color.gray)) }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    context?.let { tab?.icon?.setTint(ContextCompat.getColor(it, R.color.blue)) }
                }
            })
            tabLayout.getTabAt(1)?.select()
            tabLayout.setTabRippleColorResource(R.color.blue_animation)
        }
    }

    private fun addListButtonListener() {
        binding.newListButton.setOnClickListener {
            activity?.supportFragmentManager!!
                .beginTransaction()
                .addToBackStack(null)
                .replace(
                    (view?.parent as View).id,
                    AddNewListFragment.newInstance(),
                    "addNewListFragment"
                )
                .commit()
        }
    }
}