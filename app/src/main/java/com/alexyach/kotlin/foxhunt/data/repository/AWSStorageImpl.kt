package com.alexyach.kotlin.foxhunt.data.repository

import android.util.Log
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.amplifyframework.auth.AuthUserAttributeKey.name
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User


class AWSStorageImpl {

    fun readAllUsers(callback: (List<UserModel>) -> Unit) {
        val userList: MutableList<UserModel> = mutableListOf()

        try {
            Amplify.DataStore.query(User::class.java,
                { matches ->
                    while (matches.hasNext()) {
                        val userAws = matches.next()
                        userList.add(
                            UserModel(
                                name = userAws.name,
                                numberOfGame = userAws.numberOfGame,
                                minNumberOfMoves = userAws.minNumberOfMoves,
                                maxNumberOfMoves = userAws.maxNumberOfMoves,
                                sumNumberOfMoves = userAws.sumNumberOfMoves,
                                meanNumberOfMoves = userAws.meanNumberOfMoves
                                )
                        )
//                        Log.d("myLogs", "while (matches.hasNext()): ${userList}")
//                        callback(userList)
                    }

                    Log.d("myLogs", "Зчитування успішне userList: $userList")
                    Log.d("myLogs", "AWSStorageImpl Thread ${Thread.currentThread().name}")

                    callback(userList)
                },
                { error ->
                    Log.d("myLogs", "Помилка зчитування ", error)
                })
        } catch (e: Exception) {
            Log.d("myLogs", "Exception ", e)
        }
    }

    fun saveUser(userModel: UserModel) {
        val userAWS = User.builder()
            .name(userModel.name)
            .numberOfGame(userModel.numberOfGame)
            .minNumberOfMoves(userModel.minNumberOfMoves)
            .maxNumberOfMoves(userModel.maxNumberOfMoves)
            .sumNumberOfMoves(userModel.sumNumberOfMoves)
            .meanNumberOfMoves(userModel.meanNumberOfMoves)
            .build()
        try {
            Amplify.DataStore.save(
                userAWS,
                {Log.d("myLogs", "AWSStorageImpl, SAVE success ")},
                {Log.d("myLogs", "AWSStorageImpl, SAVE Error ")}
            )
        } catch (e: Exception) {
            Log.d("myLogs", "Помилка  ", e)
        }

    }


}