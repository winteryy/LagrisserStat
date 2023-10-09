package com.winterry.langrisserstat.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MATCH")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "date") val date: Int,
    @ColumnInfo(name = "is_first_hand") val isFirstHand: Boolean,
    @ColumnInfo(name = "is_win") val isWin: Boolean,
    @ColumnInfo(name = "memo") val memo: String?,
)