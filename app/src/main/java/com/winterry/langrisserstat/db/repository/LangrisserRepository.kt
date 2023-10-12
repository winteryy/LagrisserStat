package com.winterry.langrisserstat.db.repository

import android.content.Context
import com.winterry.langrisserstat.db.HeroData
import com.winterry.langrisserstat.db.LangrisserDb
import com.winterry.langrisserstat.db.entity.HeroVersusEntity
import com.winterry.langrisserstat.db.entity.MatchEntity

class LangrisserRepository() {

    private val db = LangrisserDb.getInstance()

    fun getHeroList(): Map<Int, String> {
        return HeroData.getHeroes()
    }

    suspend fun addMatch(match: MatchEntity) {
        db.matchDao().insertMatch(match)
    }

    //TODO fun getMatches()
    //TODO fun getHeroStat()
    //TODO fun getUserStat() 최근 선후공, 최근 승률, 총 전적
}