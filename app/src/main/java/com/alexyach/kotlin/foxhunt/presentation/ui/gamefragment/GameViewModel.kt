package com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.foxhunt.domain.model.ModelItemField
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.data.repository.AWSStorageCoroutinesImpl
import com.alexyach.kotlin.foxhunt.domain.FoxHuntGame
import com.alexyach.kotlin.foxhunt.domain.repository.IAWSStorage
import com.alexyach.kotlin.foxhunt.presentation.ui.app.AppFoxHunt
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val foxHuntGame = FoxHuntGame()
    private val storage : IAWSStorage = AWSStorageCoroutinesImpl()

    private var fieldListLiveDate: MutableLiveData<List<ModelItemField>> =
        MutableLiveData<List<ModelItemField>>()

    fun getFieldList(): MutableLiveData<List<ModelItemField>> {
        return fieldListLiveDate
    }

    // Прапорець закінтчення гри
    private var isWin: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getIsWin(): MutableLiveData<Boolean> = isWin


    private var countStep: MutableLiveData<Int> = MutableLiveData(0)
    fun getCountStep(): MutableLiveData<Int> = countStep

    init {
        createFieldGame()
    }

    private fun createFieldGame() {
        isWin.value = false
        fieldListLiveDate.value = foxHuntGame.createFox()
    }

    fun restartGame() {
        foxHuntGame.restartGame()
        countStep.value = 0

        createFieldGame()
    }

    fun action(index: Int) {
        if (foxHuntGame.checkFieldStateNoClick(index)) return

        countStep.value = countStep.value?.plus(1)
        fieldListLiveDate.value = foxHuntGame.action(index)

        if (checkWin()) {
            finishGame()
        }
    }

    // Встановлення маркера notFox
    fun checkMarkerNotFox(position: Int) {
        fieldListLiveDate.value = foxHuntGame.checkMarkerNotFox(position)
    }

    /** DataStore */
    fun saveDataStore(userModelPreferences: UserModel) {

        userModelPreferences.numberOfGame = userModelPreferences.numberOfGame + 1
        userModelPreferences.sumNumberOfMoves =
            userModelPreferences.sumNumberOfMoves + countStep.value!!

        // Перша гра
        if (userModelPreferences.maxNumberOfMoves == 0) {
            userModelPreferences.maxNumberOfMoves = countStep.value!!
        }
        if (userModelPreferences.minNumberOfMoves == 0) {
            userModelPreferences.minNumberOfMoves = countStep.value!!
        }

        // Встановлення макс/мін
        if (countStep.value!! > userModelPreferences.maxNumberOfMoves) {
            userModelPreferences.maxNumberOfMoves = countStep.value!!
        }
        if (countStep.value!! < userModelPreferences.minNumberOfMoves) {
            userModelPreferences.minNumberOfMoves = countStep.value!!
        }

        userModelPreferences.meanNumberOfMoves =
            userModelPreferences.sumNumberOfMoves * 1.0 / userModelPreferences.numberOfGame

//        Log.d("myLogs", "userModelPreferences: ${userModelPreferences}")

        viewModelScope.launch {
            AppFoxHunt.getUserDataStore().saveUserPreferences(userModelPreferences)
        }

        // AWS
       updateUser(userModelPreferences)
    }

    private fun checkWin(): Boolean {
        return foxHuntGame.checkWin()
    }

    private fun finishGame() {
        isWin.value = true
    }

    /** AWS  */
    private fun updateUser(userModel: UserModel) {
        viewModelScope.launch {
            storage.updateUser(userModel)
        }
    }

}

