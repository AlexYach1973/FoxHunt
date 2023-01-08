package com.alexyach.kotlin.foxhunt.presentation.ui.listplayers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.foxhunt.domain.repository.IAWSStorage
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse
import kotlinx.coroutines.launch

class ListPlayersViewModel(
    private val storage: IAWSStorage
    ) : ViewModel() {

    private var stateResponse: MutableLiveData<StateResponse> =
        MutableLiveData<StateResponse>()
    fun getStateResponse(): MutableLiveData<StateResponse> {
        return stateResponse
    }

    init {
        readAllUsersFromAWS()
    }

    private fun readAllUsersFromAWS() {
        stateResponse.value = StateResponse.Loading

        viewModelScope.launch {
            stateResponse.postValue(storage.readAllUsers())
        }
    }

    fun readAllUsersSorted(sort: String) {
        stateResponse.value = StateResponse.Loading

        viewModelScope.launch {
            stateResponse.postValue(storage.readAllUsersSorted(sort))
        }
    }

}