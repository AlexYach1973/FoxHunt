package com.alexyach.kotlin.foxhunt.presentation.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.foxhunt.data.datastore.UserDataStore
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.domain.repository.IAWSStorage
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val storage: IAWSStorage,
    private val dataStore: UserDataStore
    ) : ViewModel() {

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
            dataStore.saveUserPreferences(userModelPreferences)
        }
    }

    fun saveNewUserToAWS(userModel: UserModel) {
        viewModelScope.launch {
            storage.saveNewUser(userModel)
        }
    }

    private fun readAllUsersFromAWS() {
        stateResponse.value = StateResponse.Loading

        viewModelScope.launch {
            stateResponse.postValue(storage.readAllUsers())
        }
    }

}