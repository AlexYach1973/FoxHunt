package com.alexyach.kotlin.foxhunt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.databinding.ActivityMainBinding
import com.alexyach.kotlin.foxhunt.ui.gamefragment.GameFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, GameFragment.newInstance())
                .commit()
        }

    }
}