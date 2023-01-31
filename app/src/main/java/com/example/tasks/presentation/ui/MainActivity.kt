package com.example.tasks.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.databinding.ActivityMainBinding
import com.example.tasks.domain.MainRepository
import com.example.tasks.presentation.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainRepository.init(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.getLists()
//        viewModel.initDatabase()
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance(), "mainFragment")
                .commit()
        }
    }
}