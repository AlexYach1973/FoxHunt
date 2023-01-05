package com.alexyach.kotlin.foxhunt.presentation.ui

import com.alexyach.kotlin.foxhunt.data.model.UserModel

sealed class StateResponse{
    class SuccessResponse(val userList: List<UserModel>): StateResponse()
    class ErrorResponse(val error: Throwable): StateResponse()
    object Loading: StateResponse()
}
