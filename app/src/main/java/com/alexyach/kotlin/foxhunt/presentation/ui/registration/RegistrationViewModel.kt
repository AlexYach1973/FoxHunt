package com.alexyach.kotlin.foxhunt.presentation.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.data.repository.AWSStorageCoroutinesImpl
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse
import com.alexyach.kotlin.foxhunt.presentation.ui.app.AppFoxHunt
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private var stateResponse: MutableLiveData<StateResponse> =
        MutableLiveData<StateResponse>()
    fun getStateResponse(): MutableLiveData<StateResponse> {
        return stateResponse
    }

    init {
        readAllUsersFromAWS()
    }

    fun saveUserNameToDataStore(userModelPreferences: UserModel) {
        viewModelScope.launch {
            AppFoxHunt.getUserDataStore().saveUserPreferences(userModelPreferences)
        }
    }

    fun saveNewUserToAWS(userModel: UserModel) {
        viewModelScope.launch {
            AWSStorageCoroutinesImpl().saveNewUser(userModel)
        }
    }

    private fun readAllUsersFromAWS() {
        stateResponse.value = StateResponse.Loading

        viewModelScope.launch {
            stateResponse.postValue(AWSStorageCoroutinesImpl().readAllUsers())
        }
    }

}