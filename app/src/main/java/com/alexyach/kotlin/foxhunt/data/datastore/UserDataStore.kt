package com.alexyach.kotlin.foxhunt.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alexyach.kotlin.foxhunt.data.model.User
import com.alexyach.kotlin.foxhunt.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val context: Context) {
    // Create a DataStore instance using the preferencesDataStore delegate, with the Context as
    // receiver.
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    // DataStore Preferences
    suspend fun saveUserPreferences(user: User) {
        context.dataStore.edit { preferences ->
            preferences[NAME] = user.name
            preferences[NUMBER_OF_GAME] = user.numberOfGame
            preferences[MIN_NUMBER_OF_MOVES] = user.minNumberOfMoves
            preferences[MAX_NUMBER_OF_MOVES] = user.maxNumberOfMoves
            preferences[SUM_NUMBER_OF_MOVES] = user.sumNumberOfMoves
            preferences[MEAN_NUMBER_OF_MOVES] = user.meanNumberOfMoves

        }
    }

    val userName: Flow<String> = context.dataStore.data
        .map { it[NAME] ?: NAME_UNKNOWN }

    val numberOfGameGameFlow: Flow<Int> = context.dataStore.data
        .map { it[NUMBER_OF_GAME] ?: 0 }

    val minNumberOfMovesFlow: Flow<Int> = context.dataStore.data
        .map { it[MIN_NUMBER_OF_MOVES] ?: 0 }

    val maxNumberOfMovesFlow: Flow<Int> = context.dataStore.data
        .map { it[MAX_NUMBER_OF_MOVES] ?: 0 }

    val sumNumberOfMovesFlow: Flow<Int> = context.dataStore.data
        .map { it[SUM_NUMBER_OF_MOVES] ?: 0 }

    val meanNumberOfMovesFlow: Flow<Double> = context.dataStore.data
        .map { it[MEAN_NUMBER_OF_MOVES] ?: 0.0 }


}