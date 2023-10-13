package com.winterry.langrisserstat

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.winterry.langrisserstat.databinding.ActivityAddMatchBinding

class AddMatchActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddMatchBinding
    private lateinit var mDetector: GestureDetectorCompat
    private val myHeroes = mutableListOf<Int>()
    private val enemyHeroes = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDetector = GestureDetectorCompat(this, MyGestureListener())

        binding.confirmButton.setOnClickListener {
            validCheck()
        }
        binding.myHeroSelectButton.setOnClickListener {
            startActivity(Intent(this, AddHeroActivity::class.java))
        }


    }

    private fun validCheck() {
        !binding.dateTextView.text.isNullOrEmpty()&&myHeroes.isNotEmpty()&&enemyHeroes.isNotEmpty()
    }

    private fun chipCheck() {
        binding.resultChipGroup.checkedChipId==Chip.NO_ID
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }
    inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(ev: MotionEvent): Boolean {
            Log.d("확인용", "터치발생")
            val v = currentFocus
            if(v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if(!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())){
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
            return super.onSingleTapUp(ev)
        }
    }

}
