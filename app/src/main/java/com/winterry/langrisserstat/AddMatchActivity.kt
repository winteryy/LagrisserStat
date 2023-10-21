package com.winterry.langrisserstat

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.winterry.langrisserstat.databinding.ActivityAddMatchBinding
import com.winterry.langrisserstat.db.entity.MatchEntity
import com.winterry.langrisserstat.db.repository.LangrisserRepository
import com.winterry.langrisserstat.viewmodel.AddMatchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMatchBinding
    private lateinit var mDetector: GestureDetectorCompat

    private val myHeroes = mutableListOf<Int>()
    private val enemyHeroes = mutableListOf<Int>()

    private val viewModel: AddMatchViewModel by viewModels()

    private val addHeroActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            when (result.data?.getBooleanExtra("isMyHero", false)) {
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

        initButtonListeners()


    }

    private fun initButtonListeners() {
        setConfirmButton()
        setDateSelectButton()
        setHeroSelectButtons()
    }

    private fun setConfirmButton() {
        binding.confirmButton.setOnClickListener {
            if (validCheck()) {
                Snackbar.make(binding.root, "통과", Snackbar.LENGTH_SHORT).show()
                val date = binding.dateTextView.text.toString().toInt()

                val match = MatchEntity(
                    id = 0,
                    date = date,
                    isFirstHand = binding.firstHandChipGroup.checkedChipId == R.id.firstHandChip,
                    isWin = binding.resultChipGroup.checkedChipId == R.id.winChip,
                    map = mapCheck(),
                    memo = binding.memoEditText.text.toString()
                )
                viewModel.addMatch(match, myHeroes, enemyHeroes)

                setResult(RESULT_OK)
                finish()

            } else {
                Snackbar.make(binding.root, "선택 항목을 다시 확인해 주세요.", Snackbar.LENGTH_SHORT).show()
            }


        }
    }

    private fun mapCheck(): Int {
        return when(binding.mapChipGroup.checkedChipId) {
            R.id.map1Chip -> 11
            R.id.map2Chip -> 12
            R.id.map3Chip -> 13
            R.id.map4Chip -> 14
            R.id.map5Chip -> 15
            R.id.map6Chip -> 16
            R.id.map7Chip -> 17
            R.id.map8Chip -> 18
            R.id.map9Chip -> 19
            R.id.map10Chip -> 20
            else -> -1
        }
    }

    private fun setDateSelectButton() {
        binding.dateSelectButton.setOnClickListener {
            var calender = Calendar.getInstance()
            var year = calender.get(Calendar.YEAR)
            var month = calender.get(Calendar.MONTH)
            var day = calender.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, dYear, dMonth, dDay ->
                binding.dateTextView.text =
                    "$dYear${String.format("%02d", dMonth + 1)}${String.format("%02d", dDay)}"
            }, year, month, day).show()
        }
    }

    private fun setHeroSelectButtons() {
        binding.myHeroSelectButton.setOnClickListener {
            val intent = Intent(this, AddHeroActivity::class.java)
            intent.putExtra("isMyHero", true)
            if (myHeroes.size != 0) {
                intent.putExtra("selectedHeroes", myHeroes.toIntArray())
            }
            addHeroActivityResultLauncher.launch(intent)
        }

        binding.enemyHeroSelectButton.setOnClickListener {
            val intent = Intent(this, AddHeroActivity::class.java)
            intent.putExtra("isMyHero", false)
            if (enemyHeroes.size != 0) {
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

    private fun validCheck(): Boolean {
        return !(binding.dateTextView.text.isNullOrEmpty()) && myHeroes.isNotEmpty() && enemyHeroes.isNotEmpty()
                && chipsCheck()
    }

    private fun chipsCheck(): Boolean {
        return binding.resultChipGroup.checkedChipId != Chip.NO_ID &&
                binding.firstHandChipGroup.checkedChipId != Chip.NO_ID &&
                binding.mapChipGroup.checkedChipId != Chip.NO_ID
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(ev: MotionEvent): Boolean {
            Log.d("확인용", "터치발생")
            val v = currentFocus
            if (v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
            return super.onSingleTapUp(ev)
        }
    }

}
