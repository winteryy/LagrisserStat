package com.winterry.langrisserstat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.winterry.langrisserstat.databinding.ActivityAddMatchBinding

class AddMatchActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddMatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myHeroSelectButton.setOnClickListener {
            binding.myHeroLayout.isVisible = binding.myHeroLayout.isVisible.not()
        }

    }
}