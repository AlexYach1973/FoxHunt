package com.alexyach.kotlin.foxhunt.data.repository

import android.util.Log
import com.alexyach.kotlin.foxhunt.data.model.UserModel
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

class AWSStorageCoroutinesImpl {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun readAllUsers(): List<UserModel> {
        val userList: MutableList<UserModel> = mutableListOf()
        // Сортировка по cередньому значенню
        val sortedBy = QueryField.field("meanNumberOfMoves")

        Amplify.DataStore.query(User::class, Where.sorted(sortedBy.ascending()))
            .catch { error -> Log.d("myLogs", "DataStoreException: $error") }
            .collect { userAws ->
                userList.add(
                    userAWSToUserModel(userAws)
                )
            }

        return userList
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun updateUser(userModel: UserModel) {
        // Шукаемо по NAME
        val searchField = QueryField.field("User.name")

        Amplify.DataStore.query(
            User::class,
            Where.matches(searchField.eq(userModel.name))
        )
            .catch { error -> Log.d("myLogs", "saveNewUserOrUpdate(), Query Exception: $error") }
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
            .catch { error -> Log.d("myLogs", "Save Exception: $error") }
            .collect { Log.d("myLogs", "Update User: ${userModel.name}") }
    }

    suspend fun saveNewUser(userModel: UserModel) {
        try {
           Amplify.DataStore.save(usrModelToUserAWS(userModel))
           Log.d("myLogs", "Save New Use: ${userModel.name}")

       } catch (e: DataStoreException) {
           Log.d("myLogs", "DataStoreException: $e")
       }
    }

    suspend fun deleteByName(name: String) {
        val searchField = QueryField.field("User.name")

        Amplify.DataStore.query(User::class, Where.matches(searchField.eq(name)))
            .catch { error -> Log.d("myLogs", "Query Exception: $error") }
            .onEach { Amplify.DataStore.delete(it) }
            .catch { error -> Log.d("myLogs", "Delete failed: $error") }
            .collect { Log.d("myLogs", "Delete al Users") }
    }

    suspend fun deleteAllUsers() {
        Amplify.DataStore.query(User::class)
            .catch { error -> Log.d("myLogs", "Query Exception: $error") }
            .onEach { Amplify.DataStore.delete(it) }
            .catch { error -> Log.d("myLogs", "Delete failed: $error") }
            .collect { Log.d("myLogs", "Delete al Users") }
    }


}