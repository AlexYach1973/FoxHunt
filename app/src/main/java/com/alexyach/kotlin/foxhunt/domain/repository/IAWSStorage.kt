package com.alexyach.kotlin.foxhunt.domain.repository

import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse

interface IAWSStorage {
    suspend fun readAllUsers(): StateResponse
    suspend fun readAllUsersSorted(sorted: String): StateResponse
    suspend fun updateUser(userModel: UserModel)
    suspend fun saveNewUser(userModel: UserModel)
    suspend fun deleteByName(name: String)
}

