package com.example.tasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.tasks.databinding.ActivityMainBinding
import com.example.tasks.domain.repositories.MainRepository
import com.example.tasks.presentation.di.MainViewModel
import com.example.tasks.presentation.ui.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainRepository.init(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.getLists()
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance(), "mainFragment")
                .commit()
        }
    }
}