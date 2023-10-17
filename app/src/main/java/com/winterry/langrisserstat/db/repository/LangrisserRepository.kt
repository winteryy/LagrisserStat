package com.winterry.langrisserstat.db.repository

import android.content.Context
import com.winterry.langrisserstat.db.HeroData
import com.winterry.langrisserstat.db.LangrisserDb
import com.winterry.langrisserstat.db.entity.HeroVersusEntity
import com.winterry.langrisserstat.db.entity.MatchEntity
import com.winterry.langrisserstat.db.entity.MatchHeroEntity

class LangrisserRepository {

    private val db = LangrisserDb.getInstance()

    fun getHeroesMap(): Map<Int, String> {
        return HeroData.getHeroesMap()
    }

    fun getHeroes(): List<HeroData.Hero> {
        return HeroData.getHeroes()
    }

    suspend fun getMatchData(offset: Int, page: Int): List<MatchEntity> {
        val matchList = db.matchDao().loadMatch(offset, page)

        return matchList
    }

    suspend fun getMatchHeroData(matchId: Long): List<MatchHeroEntity> {
        val matchHeroList = db.matchHeroDao().loadMatchHero(matchId)

        return matchHeroList
    }

    suspend fun addMatch(match: MatchEntity): Long {
        return db.matchDao().insertMatch(match)
    }

    suspend fun addMatchHero(matchHero: MatchHeroEntity) {
        return db.matchHeroDao().insertMatchHero(matchHero)
    }

    //TODO fun getMatches()
    //TODO fun getHeroStat()
    //TODO fun getUserStat() 최근 선후공, 최근 승률, 총 전적
}

data class MatchData(
    val id: Long,
    val date: Int,
    val isFirstHand: Boolean,
    val isWin: Boolean,
    val map: Int,
    val memo: String,
    val myHeroes: List<Int>,
    val enemyHeroes: List<Int>
)