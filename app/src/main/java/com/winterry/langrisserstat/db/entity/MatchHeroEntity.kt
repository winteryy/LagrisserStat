package com.winterry.langrisserstat.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MATCH_HERO")
data class MatchHeroEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "match_id") val matchId: Long,
    @ColumnInfo(name = "hero_id") val heroId: Int,
    @ColumnInfo(name = "is_my_hero") val isMyHero: Boolean,
)