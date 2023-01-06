package com.alexyach.kotlin.foxhunt.domain

import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.domain.model.ModelItemField
import com.alexyach.kotlin.foxhunt.domain.model.StateField
import kotlin.random.Random

class FoxHuntGame {

    private val dataList: MutableList<ModelItemField> = mutableListOf()

    // Встановлення лис
    fun createFox(): List<ModelItemField> {

        // Create fields
        for (i in 1..81) {
            dataList.add(ModelItemField())
        }

        while (dataList.filter { it.isFox }.size < 5) {
            val random = (0..80).random(Random(System.nanoTime()))
            dataList[random].isFox = true
//            Log.d("myLogs", "Fox: $random")
        }

        for (i in 0 until dataList.size) {
            if (dataList[i].isFox) setCountFox(i)
        }
        return dataList
    }

    fun restartGame() {
        dataList.clear()
    }

    fun checkFieldStateNoClick(index: Int): Boolean {
        return dataList[index].viewMode != StateField.NO_CLICK
    }

    fun action(index: Int): List<ModelItemField> {
        if (dataList[index].isFox) {
            dataList[index].viewMode = StateField.SIT_FOX
            dataList[index].image = R.drawable.fox_24

        } else {
            dataList[index].viewMode = StateField.COUNT_FOX
        }
        return dataList
    }

    // Встановлення маркера notFox
    fun checkMarkerNotFox(position: Int): List<ModelItemField> {
        dataList[position].markerNotFox = !dataList[position].markerNotFox
        return dataList
    }

    fun checkWin(): Boolean {
        return dataList.filter { it.viewMode == StateField.SIT_FOX }.size == 5
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
}