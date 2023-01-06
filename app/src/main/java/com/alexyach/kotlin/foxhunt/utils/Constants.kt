package com.alexyach.kotlin.foxhunt.utils

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

const val KEY_USER_NAME = "keyUserName"

const val NAME_UNKNOWN = "unknown"

const val USER_PREFERENCES_NAME = "userPreferencesName"
val NAME = stringPreferencesKey("userName")
val NUMBER_OF_GAME = intPreferencesKey("numberOfGame")
val MIN_NUMBER_OF_MOVES = intPreferencesKey("minNumberOfMoves")
val MAX_NUMBER_OF_MOVES = intPreferencesKey("maxNumberOfMoves")
val SUM_NUMBER_OF_MOVES = intPreferencesKey("sumNumberOfMoves")
val MEAN_NUMBER_OF_MOVES = doublePreferencesKey("meanNumberOfMoves")




