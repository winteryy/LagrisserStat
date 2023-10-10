package com.winterry.langrisserstat.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.winterry.langrisserstat.db.entity.MatchHeroEntity

@Dao
interface MatchHeroDao {

    @Insert
    suspend fun insertMatchHero(matchHero: MatchHeroEntity)

}