package com.winterry.langrisserstat.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.winterry.langrisserstat.db.entity.MatchEntity

@Dao
interface MatchDao {

    @Insert
    suspend fun insertMatch(match: MatchEntity): Long

    @Delete
    suspend fun deleteMatch(match: MatchEntity)

    @Query("SELECT * FROM `MATCH` ORDER BY date DESC, id DESC LIMIT :offset, :page")
    suspend fun loadMatch(offset: Int, page: Int): List<MatchEntity>
}