package com.alexyach.kotlin.foxhunt.data.model

/*@Parcelize*/
data class UserModel(
    var name: String,
    var numberOfGame: Int,
    var minNumberOfMoves: Int,
    var maxNumberOfMoves: Int,
    var sumNumberOfMoves: Int,
    var meanNumberOfMoves: Double
) /*: Parcelable*/
