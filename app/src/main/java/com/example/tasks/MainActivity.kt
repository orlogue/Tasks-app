package com.example.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasks.databinding.ActivityMainBinding
import com.example.tasks.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(main.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}