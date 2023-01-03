package com.alexyach.kotlin.foxhunt.utils

import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.amplifyframework.datastore.generated.model.User

fun usrModelToUserAWS(userModel: UserModel): User {
    return User.builder()
        .name(userModel.name)
        .numberOfGame(userModel.numberOfGame)
        .minNumberOfMoves(userModel.minNumberOfMoves)
        .maxNumberOfMoves(userModel.maxNumberOfMoves)
        .sumNumberOfMoves(userModel.sumNumberOfMoves)
        .meanNumberOfMoves(userModel.meanNumberOfMoves)
        .build()
}

fun userAWSToUserModel(userAWS: User): UserModel {
    return UserModel(
        name = userAWS.name,
        numberOfGame = userAWS.numberOfGame,
        minNumberOfMoves = userAWS.minNumberOfMoves,
        maxNumberOfMoves = userAWS.maxNumberOfMoves,
        sumNumberOfMoves = userAWS.sumNumberOfMoves,
        meanNumberOfMoves = userAWS.meanNumberOfMoves
    )
}