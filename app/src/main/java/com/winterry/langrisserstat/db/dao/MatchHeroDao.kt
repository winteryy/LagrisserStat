package com.winterry.langrisserstat.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.winterry.langrisserstat.db.entity.MatchHeroEntity

@Dao
interface MatchHeroDao {

    @Insert
    suspend fun insertMatchHero(matchHero: MatchHeroEntity)

    @Query("SELECT * FROM MATCH_HERO WHERE match_id = :matchId")
    suspend fun loadMatchHero(matchId: Long): List<MatchHeroEntity>

    @Query("DELETE FROM MATCH_HERO WHERE match_id = :matchId")
    suspend fun deleteMatchHero(matchId: Long)


}