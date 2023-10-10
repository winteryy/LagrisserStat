package com.winterry.langrisserstat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.winterry.langrisserstat.db.dao.HeroVersusDao
import com.winterry.langrisserstat.db.dao.MatchDao
import com.winterry.langrisserstat.db.dao.MatchHeroDao
import com.winterry.langrisserstat.db.entity.HeroVersusEntity
import com.winterry.langrisserstat.db.entity.MatchEntity
import com.winterry.langrisserstat.db.entity.MatchHeroEntity

@Database(
    entities = [MatchEntity::class, MatchHeroEntity::class, HeroVersusEntity::class],
    version = 1
)
abstract class LangrisserDb : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun matchHeroDao(): MatchHeroDao
    abstract fun heroVersusDao(): HeroVersusDao

    companion object {
        @Volatile
        private var INSTANCE: LangrisserDb? = null

        @Synchronized
        fun getInstance(context: Context): LangrisserDb {
            return INSTANCE ?: synchronized(LangrisserDb::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LangrisserDb::class.java,
                    "langrisser.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}