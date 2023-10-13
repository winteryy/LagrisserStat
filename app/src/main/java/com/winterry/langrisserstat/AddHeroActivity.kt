package com.winterry.langrisserstat

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.winterry.langrisserstat.adapter.HeroAdapter
import com.winterry.langrisserstat.databinding.ActivityAddHeroBinding
import com.winterry.langrisserstat.db.HeroData
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddHeroActivity: AppCompatActivity(), HeroAdapter.OnItemClickListener {

    private lateinit var binding: ActivityAddHeroBinding
    private lateinit var heroAdapter: HeroAdapter
    private val selectedHero = mutableListOf<Int>()
    private val allHeroList: List<HeroData.Hero> = HeroData.getHeroes().map {
        it.copy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        heroAdapter = HeroAdapter(this)

        binding.heroRecyclerView.apply {
            layoutManager = GridLayoutManager(this@AddHeroActivity, 3)
            adapter = heroAdapter
        }

        Log.d("AddHeroActivity", allHeroList.toString())

        heroAdapter.submitList(allHeroList)

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

    private fun AppCompatImageView.setHeroImage(heroId: Int) {
        load(
            context.resources.getIdentifier("hero_$heroId", "drawable", packageName)
        )
        isVisible = true
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