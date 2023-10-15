package com.winterry.langrisserstat

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
    private val addHeroActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.resultCode == RESULT_OK) {
            when(result.data?.getBooleanExtra("isMyHero", false)) {
                true -> {
                    myHeroes.clear()
                    for (id in result.data?.getIntArrayExtra("selectedHeroes")!!) {
                        myHeroes.add(id)
                    }
                    setMyHeroes()
                }
                false -> {
                    enemyHeroes.clear()
                    for (id in result.data?.getIntArrayExtra("selectedHeroes")!!) {
                        enemyHeroes.add(id)
                    }
                    setEnemyHeroes()
                }
                else -> return@registerForActivityResult
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDetector = GestureDetectorCompat(this, MyGestureListener())

        binding.confirmButton.setOnClickListener {
            validCheck()
        }
        binding.myHeroSelectButton.setOnClickListener {
            val intent = Intent(this, AddHeroActivity::class.java)
            intent.putExtra("isMyHero", true)
            if(myHeroes.size!=0) {
                intent.putExtra("selectedHeroes", myHeroes.toIntArray())
            }
            addHeroActivityResultLauncher.launch(intent)
        }
        binding.enemyHeroSelectButton.setOnClickListener {
            val intent = Intent(this, AddHeroActivity::class.java)
            intent.putExtra("isMyHero", false)
            if(enemyHeroes.size!=0) {
                intent.putExtra("selectedHeroes", enemyHeroes.toIntArray())
            }
            addHeroActivityResultLauncher.launch(intent)
        }

    }

    private fun setMyHeroes() {
        binding.myHeroLayout.isVisible = true
        binding.myHero1ImageView.setHeroImage(myHeroes[0])
        binding.myHero2ImageView.setHeroImage(myHeroes[1])
        binding.myHero3ImageView.setHeroImage(myHeroes[2])
        binding.myHero4ImageView.setHeroImage(myHeroes[3])
        binding.myHero5ImageView.setHeroImage(myHeroes[4])
    }

    private fun setEnemyHeroes() {
        binding.enemyHeroLayout.isVisible = true
        binding.enemyHero1ImageView.setHeroImage(enemyHeroes[0])
        binding.enemyHero2ImageView.setHeroImage(enemyHeroes[1])
        binding.enemyHero3ImageView.setHeroImage(enemyHeroes[2])
        binding.enemyHero4ImageView.setHeroImage(enemyHeroes[3])
        binding.enemyHero5ImageView.setHeroImage(enemyHeroes[4])
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
