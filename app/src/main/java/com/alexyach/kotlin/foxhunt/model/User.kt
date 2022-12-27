package com.alexyach.kotlin.foxhunt.model

/*@Parcelize*/
data class User(
    var numberOfGame: Int,
    var minNumberOfMoves: Int,
    var maxNumberOfMoves: Int,
    var sumNumberOfMoves: Int,
    var meanNumberOfMoves: Double
) /*: Parcelable*/
