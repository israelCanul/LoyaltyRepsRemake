package com.xcaret.loyaltyreps.data.config

import android.content.Context
import android.content.SharedPreferences
import com.xcaret.loyaltyreps.data.config.BaseSharePref.ValueType.*


abstract class BaseSharePref {
    abstract fun getSettingName(): String

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(getSettingName(), Context.MODE_PRIVATE)
    }

    fun setValue(key: String, value: Any, context: Context, type: ValueType = STRING){
        val editor = getSharedPreferences(context).edit()
        when(type){
            STRING -> { editor.putString(key, value.toString())}
            INT -> { editor.putInt(key, value as Int)}
            BOOLEAN -> { editor.putBoolean(key, value as Boolean)}
            FLOAT -> { editor.putFloat(key, value as Float)}
            else -> {}
        }
        editor.apply()
    }

    enum class ValueType {
        STRING,
        INT,
        BOOLEAN,
        FLOAT,
        LONG
    }
}