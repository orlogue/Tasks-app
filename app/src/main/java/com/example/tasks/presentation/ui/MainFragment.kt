package com.example.tasks.presentation.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.tasks.R
import com.example.tasks.databinding.FragmentMainBinding
import com.example.tasks.presentation.MainViewModel
import com.example.tasks.presentation.ui.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        initializePager()
        addListButtonListener()
        addPlusButtonListener()

        return binding.root
    }


    private fun initializePager() {
        binding.apply {
            viewModel.lists.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Log.d("MyLog2", it.toList().toString())
                    viewModel.viewModelScope.launch {
                        pager.adapter = PagerAdapter(requireActivity(), it)
                        TabLayoutMediator(tabLayout, pager) { tab, pos ->
                            for (i in 0 until it.size) {
                                if (pos != 0)
                                    tab.setText(it[pos].name)
                                else
                                    tab.setIcon(R.drawable.star_24)
                            }
                        }.attach()
                    }
                }
            }

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

    private fun addPlusButtonListener() {
        binding.plusButton.setOnClickListener {
            val fragment = NewNoteFragment();
            fragment.arguments = Bundle().apply {
                putInt(NewNoteFragment.LIST_ID, binding.pager.currentItem + 1)
            };
            fragment.show(childFragmentManager, null)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}