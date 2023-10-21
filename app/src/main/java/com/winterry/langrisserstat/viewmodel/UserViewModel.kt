package com.winterry.langrisserstat.viewmodel

import androidx.lifecycle.ViewModel
import com.winterry.langrisserstat.db.repository.LangrisserRepository

class UserViewModel: ViewModel() {

    private val langRepo = LangrisserRepository()

}