package com.winterry.langrisserstat.db.repository

import android.content.Context
import com.winterry.langrisserstat.db.HeroData
import com.winterry.langrisserstat.db.LangrisserDb
import com.winterry.langrisserstat.db.entity.HeroVersusEntity

class LangrisserRepository(db: LangrisserDb) {

    fun getHeroList(): Map<Int, String> {
        return HeroData.getHeroes()
    }

    //TODO fun getMatches()
    //TODO fun getHeroStat()
    //TODO fun getUserStat() 최근 선후공, 최근 승률, 총 전적
}