package com.winterry.langrisserstat

import android.app.Application
import android.content.Context

class LangrisserStatApplication: Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: LangrisserStatApplication
        fun getApplicationContext(): Context {
            return instance.applicationContext
        }
    }
}