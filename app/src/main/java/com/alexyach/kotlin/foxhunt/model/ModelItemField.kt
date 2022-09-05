package com.alexyach.kotlin.foxhunt.model

import com.alexyach.kotlin.foxhunt.R

data class ModelItemField(
    var isFox: Boolean = false,
    var markerNotFox: Boolean = false,
    var countFox: Int = 0,
    var modeView: StateField = StateField.NO_CLICK,
    var image: Int = R.drawable.plant
)
