package com.away0x.calculation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlin.random.Random

class CalcViewModel(val context: Application, val handle: SavedStateHandle): AndroidViewModel(context) {

    private val KEY_HIGH_SCORE = "key_high_score"
    private val KEY_LEFT_NUMBER = "key_left_number"
    private val KEY_RIGHT_NUMBER = "key_right_number"
    private val KEY_OPERATOR = "key_operator"
    private val KEY_ANSWER = "key_answer"
    private val KEY_CURRENT_SCORE = "key_current_score"
    private val SAVE_SHP_DATA_NAME = "save_shp_data_name"

    var winFlag = false

    init {
        if (!handle.contains(KEY_HIGH_SCORE)) {
            val shp = getApplication<Application>().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
            handle.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0))
            handle.set(KEY_LEFT_NUMBER, 0)
            handle.set(KEY_RIGHT_NUMBER, 0)
            handle.set(KEY_OPERATOR, "+")
            handle.set(KEY_ANSWER, 0)
            handle.set(KEY_CURRENT_SCORE, 0)
        }
    }

    fun generator() {
        val LEVEL = 20
        val x = Random.nextInt(LEVEL) + 1
        val y = Random.nextInt(LEVEL) + 1

        if (x%2 == 0){
            getOperator().value = "+"
            if (x > y) {
                getAnswer().value = x
                getLeftNumber().value = y
                getRightNumber().value = x-y
            } else {
                getAnswer().value = y
                getLeftNumber().value = x
                getRightNumber().value = y-x
            }
        } else {
            getOperator().value = "-"
            if (x > y) {
                getLeftNumber().value = x
                getRightNumber().value = y
                getAnswer().value = x - y
            } else {
                getLeftNumber().value = y
                getRightNumber().value = x
                getAnswer().value = y-x
            }
        }
    }

    fun saveHighScore() {
        val shp = getApplication<Application>().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
        val edit = shp.edit()
        edit.putInt(KEY_HIGH_SCORE, getHighScore().value!!.toInt())
        edit.apply()
    }

    fun answerCorrect() {
        getCurrentScore().value = getCurrentScore().value!! + 1
        if (getCurrentScore().value!! > getHighScore().value!!) {
            getHighScore().value = getCurrentScore().value
            winFlag = true;
        }

        generator()
    }

    fun getLeftNumber(): MutableLiveData<Int> {
        return handle.getLiveData(KEY_LEFT_NUMBER)
    }

    fun getRightNumber(): MutableLiveData<Int> {
        return handle.getLiveData(KEY_RIGHT_NUMBER)
    }

    fun getOperator(): MutableLiveData<String> {
        return handle.getLiveData(KEY_OPERATOR)
    }

    fun getHighScore(): MutableLiveData<Int> {
        return handle.getLiveData(KEY_HIGH_SCORE)
    }

    fun getCurrentScore(): MutableLiveData<Int> {
        return handle.getLiveData(KEY_CURRENT_SCORE)
    }

    fun getAnswer(): MutableLiveData<Int> {
        return handle.getLiveData(KEY_ANSWER)
    }

}