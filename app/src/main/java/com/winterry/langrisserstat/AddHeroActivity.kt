package com.winterry.langrisserstat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar

import com.winterry.langrisserstat.adapter.HeroAdapter
import com.winterry.langrisserstat.databinding.ActivityAddHeroBinding
import com.winterry.langrisserstat.db.HeroData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddHeroActivity: AppCompatActivity(), HeroAdapter.OnItemClickListener {

    private lateinit var binding: ActivityAddHeroBinding
    private lateinit var heroAdapter: HeroAdapter
    private var isMyHero: Boolean = false
    private val selectedHero = mutableListOf<Int>()
    private val allHeroList: List<HeroData.Hero> by lazy {
        if(selectedHero.isEmpty()) {
            HeroData.getHeroes().map {
                it.copy()
            }
        } else {
            HeroData.getHeroes().map {
                it.copy().apply {
                    if(selectedHero.contains(this.id)) {
                        this.selected = true
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        heroAdapter = HeroAdapter(this)
        getIntentExtras()


        Log.d("AddHeroActivity", allHeroList.toString())

        initRecyclerView()
        setEditText()

        binding.confirmButton.setOnClickListener {
            if(selectedHero.size==5){
                val intent = Intent(this, AddMatchActivity::class.java).apply {
                    putExtra("selectedHeroes", selectedHero.toIntArray())
                    putExtra("isMyHero", isMyHero)
                }
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Snackbar.make(binding.root, "5명의 영웅이 선택 되어야 합니다.", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun getIntentExtras() {
        isMyHero = intent.getBooleanExtra("isMyHero", false)
        if (intent.getIntArrayExtra("selectedHeroes")?.isNotEmpty() == true) {
            for (id in intent.getIntArrayExtra("selectedHeroes")!!) {
                selectedHero.add(id)
            }
            setSelectedHeroes()
        }
        Log.d("getIntentExtras", selectedHero.toString())
    }

    private fun initRecyclerView() {
        binding.heroRecyclerView.apply {
            layoutManager = GridLayoutManager(this@AddHeroActivity, 3)
            adapter = heroAdapter
        }
        heroAdapter.submitList(allHeroList)
    }

    private fun setEditText() {
        binding.searchHeroEditText.addCustomTextChangedListener(500) {
            heroAdapter.submitList(allHeroList.filter { hero ->
                hero.name.contains(it)
            })
        }
    }

    private fun setSelectedHeroes() {
        for(i in 0 .. 4) {
            try {
                when(i) {
                    0 -> binding.hero1ImageView.setHeroImage(selectedHero[i])
                    1 -> binding.hero2ImageView.setHeroImage(selectedHero[i])
                    2 -> binding.hero3ImageView.setHeroImage(selectedHero[i])
                    3 -> binding.hero4ImageView.setHeroImage(selectedHero[i])
                    4 -> binding.hero5ImageView.setHeroImage(selectedHero[i])
                }
            } catch (e: IndexOutOfBoundsException) {
                when(i) {
                    0 -> binding.hero1ImageView.visibility = View.INVISIBLE
                    1 -> binding.hero2ImageView.visibility = View.INVISIBLE
                    2 -> binding.hero3ImageView.visibility = View.INVISIBLE
                    3 -> binding.hero4ImageView.visibility = View.INVISIBLE
                    4 -> binding.hero5ImageView.visibility = View.INVISIBLE
                    else -> return
                }
            }
        }
    }

    override fun onItemClick(item: HeroData.Hero) {
        if(item.id in selectedHero) {
            selectedHero.remove(item.id)
            item.selected = false
            setSelectedHeroes()
        } else {
            if(selectedHero.size<5) {
                selectedHero.add(item.id)
                item.selected = true
                setSelectedHeroes()
            }
        }
        Log.d("AddHeroActivity", "Current List is: $selectedHero")
    }

    private fun EditText.addCustomTextChangedListener(delay: Long, search: (String) -> Unit) {
        var job: Job? = null
        this.addTextChangedListener {
            it ?: return@addTextChangedListener
            job?.cancel()
            job = lifecycleScope.launch {
                delay(delay)
                search(it.toString())
            }
        }
    }
}

