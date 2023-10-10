package com.winterry.langrisserstat.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.winterry.langrisserstat.db.entity.MatchEntity

@Dao
interface MatchDao {

    @Insert
    suspend fun insertMatch(match: MatchEntity): Long

    @Delete
    suspend fun deleteMatch(match: MatchEntity)

}