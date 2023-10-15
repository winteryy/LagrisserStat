package com.winterry.langrisserstat

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import coil.load

fun AppCompatImageView.setHeroImage(heroId: Int) {
    load(
        context.resources.getIdentifier("hero_$heroId", "drawable", context.packageName)
    )
    isVisible = true
}