package com.example.tasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(main.frameLayout.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}