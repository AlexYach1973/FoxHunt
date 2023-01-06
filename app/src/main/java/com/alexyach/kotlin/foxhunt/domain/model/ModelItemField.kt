package com.alexyach.kotlin.foxhunt.domain.model

import com.alexyach.kotlin.foxhunt.R

data class ModelItemField(
    var isFox: Boolean = false,
    var markerNotFox: Boolean = false,
    var countFox: Int = 0,
    var viewMode: StateField = StateField.NO_CLICK,
    var image: Int = R.drawable.plant
)
