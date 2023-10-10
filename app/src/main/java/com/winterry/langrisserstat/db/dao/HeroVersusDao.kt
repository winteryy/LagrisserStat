package com.winterry.langrisserstat.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.winterry.langrisserstat.db.entity.HeroVersusEntity

@Dao
interface HeroVersusDao {

    @Insert
    suspend fun insertHeroVersus(heroVersus: HeroVersusEntity)

    @Update
    suspend fun updateHeroVersus(heroVersus: HeroVersusEntity)

}