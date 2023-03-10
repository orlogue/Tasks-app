package com.example.tasks.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.tasks.R
import com.example.tasks.databinding.FragmentMainBinding
import com.example.tasks.presentation.di.MainViewModel
import com.example.tasks.presentation.common.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private var previousPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        initializePager()
        addListButtonListener()
        addPlusButtonListener()
        optionsButtonListener()

        return binding.root
    }

    private fun initializePager() {
        binding.apply {
            viewModel.lists.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Log.d("MyLog2", it.toList().toString())
                    pager.adapter = PagerAdapter(requireActivity(), it)

                    TabLayoutMediator(tabLayout, pager) { tab, pos ->
                        if (pos != 0) {
                            tab.text = it[pos].name
                        } else {
                            tab.setIcon(R.drawable.star_24)
                        }
                    }.attach()
                    tabLayout.getTabAt(previousPosition)?.select()
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

            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    previousPosition = position
                }
            })
        }
    }

    private fun addListButtonListener() {
        binding.newListButton.setOnClickListener {
            viewModel.lists.value?.let { previousPosition = it.size }
            activity?.supportFragmentManager!!
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(
                    (view?.parent as View).id,
                    CreateRenameListFragment.newInstance(1),
                    "addNewListFragment"
                )
                .commit()
        }
    }

    private fun addPlusButtonListener() {
        binding.plusButton.setOnClickListener {
            val fragment = NewNoteFragment()
            fragment.arguments = Bundle().apply {
                viewModel.lists.value?.get(binding.pager.currentItem)?.let { it ->
                    putInt(NewNoteFragment.LIST_ID, it.id)
                }
            }
            fragment.show(childFragmentManager, null)
        }
    }

    private fun optionsButtonListener() {
        binding.optionsButton.setOnClickListener {
            previousPosition = binding.pager.currentItem
            val fragment = OptionsFragment()
            fragment.arguments = Bundle().apply {
                viewModel.lists.value?.get(binding.pager.currentItem)?.let { it ->
                    putInt(NewNoteFragment.LIST_ID, it.id)
                    Log.d("ID", it.id.toString())
                }
            }
            fragment.show(childFragmentManager, null)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}