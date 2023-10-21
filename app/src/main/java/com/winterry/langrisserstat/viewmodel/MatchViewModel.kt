package com.winterry.langrisserstat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.winterry.langrisserstat.db.repository.LangrisserRepository
import com.winterry.langrisserstat.db.repository.MatchData
import com.winterry.langrisserstat.db.repository.MatchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MatchViewModel: ViewModel() {

    private val langRepo = LangrisserRepository()

    val items: Flow<PagingData<MatchData>> = Pager(
        config = PagingConfig(20),
        pagingSourceFactory = {
            MatchPagingSource(langRepo)
        }
    )
        .flow
        .cachedIn(viewModelScope)

    fun deleteMatch(matchId: Long) {
        viewModelScope.launch {
            langRepo.deleteMatchAndMatchHeroes(matchId)
        }

    }
}