package com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.data.model.ModelItemField
import com.alexyach.kotlin.foxhunt.data.model.StateField
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.data.repository.AWSStorageCoroutinesImpl
import com.alexyach.kotlin.foxhunt.presentation.ui.app.AppFoxHunt
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

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

    private val dataList: MutableList<ModelItemField> = mutableListOf()

    init {
        createFieldGame()
    }

    private fun createFieldGame() {
        isWin.value = false

        for (i in 1..81) {
            dataList.add(ModelItemField())
        }

        // Встановлення лис
        createFox()

        fieldListLiveDate.value = dataList
    }

    fun restartGame() {
        dataList.clear()
        countStep.value = 0

        createFieldGame()
    }

    fun action(index: Int) {
        countStep.value = countStep.value?.plus(1)

        if (dataList[index].viewMode != StateField.NO_CLICK) return

        if (dataList[index].isFox) {
            dataList[index].viewMode = StateField.SIT_FOX
            dataList[index].image = R.drawable.fox_24

        } else {
            dataList[index].viewMode = StateField.COUNT_FOX
        }

        if (chekWin()) {
            finishGame()
        }

    }

    // Встановлення маркера notFox
    fun checkMarkerNotFox(position: Int) {
        dataList[position].markerNotFox = !dataList[position].markerNotFox

    }

    // Збереження DataStore
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

    private fun chekWin(): Boolean {
        return dataList.filter { it.viewMode == StateField.SIT_FOX }.size == 5
    }

    private fun finishGame() {
        isWin.value = true
    }

    // Встановлення лис
    private fun createFox() {
        while (dataList.filter { it.isFox }.size < 5) {
            val random = (0..80).random(Random(System.nanoTime()))
            dataList[random].isFox = true

//            Log.d("myLogs", "Fox: $random")
        }

        for (i in 0 until dataList.size) {
            if (dataList[i].isFox) setCountFox(i)
        }

    }

    /** Розміщення по полю countFox - кількості лис, які видно з кожного поля */
    private fun setCountFox(index: Int) {

        // По вертикалі
        for (i in (index % 9)..80 step 9) {
            dataList[i].countFox += 1
        }
        // По горизонталі
        for (i in (index / 9) * 9..(index / 9) * 9 + 8) {
            dataList[i].countFox += 1
        }

        /** Головна діагональ */
        if (index % 10 == 0) {
            for (i in 0..80 step 10) {
                dataList[i].countFox += 1
            }
        }
        // Верхній правий трикутник
        else if (index / 10 + index % 10 < 9) {

            for (i in (index % 10)..(80 - (index % 10 - 1) * 10) step 10) {
                dataList[i].countFox += 1
            }
        } else {

            // Нижній лівий трикутник
            for (i in (index - (index % 10 + index / 10 - 9) * 10)..80 step 10) {
                dataList[i].countFox += 1
            }
        }

        /** Другорядна діагональ */
        if (index % 8 == 0 && index != 0 && index != 80) {
            for (i in 8..79 step 8) {
                dataList[i].countFox += 1
            }

            // Верхній лівий трикутник
        } else if ((index + index % 9 * 10) < 80) {
            for (i in index % 8..(index % 8 * 10) step 8) {
                dataList[i].countFox += 1
            }

            // Нижній правий трикутник
        } else {
            val initValue = (index / 9) + (index % 9) + 8 * ((index / 9) + (index % 9) - 8)
            for (i in (initValue)..80 step 8) {
                dataList[i].countFox += 1
            }
        }
//            Log.d("myLogs", "test: $test -  ${ (test/9) + (test%9) + 8 * ((test/9) + (test%9) - 8)}")
    }

    /** AWS  */
    fun updateUser(userModel: UserModel) {
        viewModelScope.launch {
            AWSStorageCoroutinesImpl().updateUser(userModel)
        }
    }

}

