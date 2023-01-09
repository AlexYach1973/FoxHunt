package com.alexyach.kotlin.foxhunt.data.repository

import android.util.Log
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.domain.repository.IAWSStorage
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse
import com.alexyach.kotlin.foxhunt.utils.TAG
import com.alexyach.kotlin.foxhunt.utils.userAWSToUserModel
import com.alexyach.kotlin.foxhunt.utils.usrModelToUserAWS
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreException
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class AWSStorageCoroutinesImpl: IAWSStorage {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun readAllUsers(): StateResponse {
        val userList: MutableList<UserModel> = mutableListOf()

        Amplify.DataStore.query(User::class)
            .catch { error ->
                     Log.d(TAG, "DataStoreException: $error")
//                StateResponse.ErrorResponse(error) ???
            }
            .collect { userAws ->
                userList.add(
                    userAWSToUserModel(userAws)
                )
            }

        return StateResponse.SuccessResponse(userList)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun readAllUsersSorted(sorted: String): StateResponse {
        val userList: MutableList<UserModel> = mutableListOf()

        // Сортировка по
        val sortedBy = QueryField.field(sorted)

        Amplify.DataStore.query(User::class, Where.sorted(sortedBy.ascending()))
            .catch { error ->
                Log.d(TAG, "DataStoreException: $error")
//                StateResponse.ErrorResponse(error) ???
            }
            .collect { userAws ->
                userList.add(
                    userAWSToUserModel(userAws)
                )
            }

        return StateResponse.SuccessResponse(userList)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun updateUser(userModel: UserModel) {
        // Шукаемо по NAME
        val searchField = QueryField.field("User.name")

        Amplify.DataStore.query(
            User::class,
            Where.matches(searchField.eq(userModel.name))
        )
            .catch { error -> Log.d(TAG, "saveNewUserOrUpdate(), Query Exception: $error") }
            .map {
                it.copyOfBuilder()
//                .name(userModel.name)
                    .numberOfGame(userModel.numberOfGame)
                    .minNumberOfMoves(userModel.minNumberOfMoves)
                    .maxNumberOfMoves(userModel.maxNumberOfMoves)
                    .sumNumberOfMoves(userModel.sumNumberOfMoves)
                    .meanNumberOfMoves(userModel.meanNumberOfMoves)
                    .build()
            }
            .onEach { Amplify.DataStore.save(it) }
            .catch { error -> Log.d(TAG, "Save Exception: $error") }
            .collect { Log.d(TAG, "Update User: ${userModel.name}") }
    }

    override suspend fun saveNewUser(userModel: UserModel) {
        try {
           Amplify.DataStore.save(usrModelToUserAWS(userModel))
           Log.d(TAG, "AWSStorageCoroutinesImpl, Save New Use: ${userModel.name}")

       } catch (e: DataStoreException) {
           Log.d(TAG, "DataStoreException: $e")
       }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun deleteByName(name: String) {
        val searchField = QueryField.field("User.name")

        Amplify.DataStore.query(User::class, Where.matches(searchField.eq(name)))
            .catch { error -> Log.d(TAG, "Query Exception: $error") }
            .onEach { Amplify.DataStore.delete(it) }
            .catch { error -> Log.d(TAG, "Delete failed: $error") }
            .collect { Log.d(TAG, "Delete al Users") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun deleteAllUsers() {
        Amplify.DataStore.query(User::class)
            .catch { error -> Log.d(TAG, "Query Exception: $error") }
            .onEach { Amplify.DataStore.delete(it) }
            .catch { error -> Log.d(TAG, "Delete failed: $error") }
            .collect { Log.d(TAG, "Delete al Users") }
    }

}