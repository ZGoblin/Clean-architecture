package com.example.cleanarch.presentationlayer

import androidx.annotation.ColorInt

data class InfoColors(
    @ColorInt val titleColor: Int,
    @ColorInt val descriptionColor: Int
)

data class InfoUiModel(
    val title: String,
    val description: String,
    val colors: InfoColors
)