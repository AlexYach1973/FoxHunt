package com.alexyach.kotlin.foxhunt.model

import com.alexyach.kotlin.foxhunt.R

data class ModelItemField(
    val coordinateX: Int = 0,
    val coordinateY: Int = 0,
    var isFox: Boolean = false,
    var countFox: Int = 0,
    var modeView: StateField = StateField.NO_CLICK,
    var image: Int = R.drawable.ic_not_pressed
)
