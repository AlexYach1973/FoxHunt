package com.alexyach.kotlin.foxhunt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val dataStore: DataStore<Preferences>) {

    // DataStore Preferences
    suspend fun saveUserPreferences(userModel: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME] = userModel.name
            preferences[NUMBER_OF_GAME] = userModel.numberOfGame
            preferences[MIN_NUMBER_OF_MOVES] = userModel.minNumberOfMoves
            preferences[MAX_NUMBER_OF_MOVES] = userModel.maxNumberOfMoves
            preferences[SUM_NUMBER_OF_MOVES] = userModel.sumNumberOfMoves
            preferences[MEAN_NUMBER_OF_MOVES] = userModel.meanNumberOfMoves

        }
    }

    val userName: Flow<String> = dataStore.data
        .map { it[NAME] ?: NAME_UNKNOWN}

    val numberOfGameGameFlow: Flow<Int> = dataStore.data
        .map { it[NUMBER_OF_GAME] ?: 0 }

    val minNumberOfMovesFlow: Flow<Int> = dataStore.data
        .map { it[MIN_NUMBER_OF_MOVES] ?: 0 }

    val maxNumberOfMovesFlow: Flow<Int> = dataStore.data
        .map { it[MAX_NUMBER_OF_MOVES] ?: 0 }

    val sumNumberOfMovesFlow: Flow<Int> = dataStore.data
        .map { it[SUM_NUMBER_OF_MOVES] ?: 0 }

    val meanNumberOfMovesFlow: Flow<Double> = dataStore.data
        .map { it[MEAN_NUMBER_OF_MOVES] ?: 0.0 }

}