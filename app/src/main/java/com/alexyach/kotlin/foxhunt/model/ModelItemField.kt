package com.alexyach.kotlin.foxhunt.model

data class ModelItemField(
    val coordinateX: Int = 0,
    val coordinateY: Int = 0,
    val countFox: Int,
    var modeView: StateField = StateField.NO_CLICK,
    var image: String /*= "@drawable/ic_launcher_foreground.xml"*/
)
