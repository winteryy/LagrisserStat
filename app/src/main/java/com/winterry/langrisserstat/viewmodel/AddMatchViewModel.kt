package com.winterry.langrisserstat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winterry.langrisserstat.db.LangrisserDb
import com.winterry.langrisserstat.db.entity.MatchEntity
import com.winterry.langrisserstat.db.entity.MatchHeroEntity
import com.winterry.langrisserstat.db.repository.LangrisserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddMatchViewModel: ViewModel() {

    private val langRepo = LangrisserRepository()

    fun addMatch(match: MatchEntity, myHeroes: List<Int>, enemyHeroes: List<Int>) {
        var tableId = -1L
        val insertMatchJob = viewModelScope.async(Dispatchers.IO) {
            tableId = langRepo.addMatch(match)
        }

        viewModelScope.launch(Dispatchers.IO) {
            insertMatchJob.await()

            myHeroes.forEach {
                langRepo.addMatchHero(MatchHeroEntity(0, tableId, it, true))
            }
            enemyHeroes.forEach {
                langRepo.addMatchHero(MatchHeroEntity(0, tableId, it, false))
            }
        }
    }
}