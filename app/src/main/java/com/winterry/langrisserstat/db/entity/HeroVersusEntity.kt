package com.winterry.langrisserstat.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HERO_VERSUS", primaryKeys = ["my_hero_id", "enemy_hero_id"])
data class HeroVersusEntity(
    @ColumnInfo(name = "my_hero_id") val myHeroId: Int,
    @ColumnInfo(name = "enemy_hero_id") val enemyHeroId: Int,
    @ColumnInfo(name = "win_count") val winCount: Int,
    @ColumnInfo(name = "total_count") val totalCount: Int,
)