package com.winterry.langrisserstat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winterry.langrisserstat.db.LangrisserDb
import com.winterry.langrisserstat.db.entity.MatchEntity
import com.winterry.langrisserstat.db.repository.LangrisserRepository
import kotlinx.coroutines.launch

class AddMatchViewModel: ViewModel() {

    private val langRepo = LangrisserRepository()

    fun addMatch(match: MatchEntity) {
        viewModelScope.launch {
            langRepo.addMatch(match)
        }
    }
}