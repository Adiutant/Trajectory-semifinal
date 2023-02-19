package com.study.trajectory_semifinal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.study.trajectory_semifinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
    }
}