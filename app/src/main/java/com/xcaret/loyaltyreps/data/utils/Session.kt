package com.xcaret.loyaltyreps.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.data.config.BaseSharePref


object Session: BaseSharePref() {
    private const val APP_SESSION = "APP_SESSION"
    private const val UID = "UID"
    private const val RCX = "RCX"
    private const val REPID = "REPID"
    private const val IDTOKEN = "IDTOKEN"
    private const val DATE_REMEMBER_INCOMPLETE_PROFILE = "DATE_REMEMBER_INCOMPLETE_PROFILE"
    private const val SHOW_WELCOME_ALERT = "SHOW_WELCOME_ALERT"
    private const val IS_VISITOR = "IS_VISITOR"
    private const val COUNTRY_CODE ="COUNTRY_CODE"
    private const val QUIZZES = "QUIZZES"
    private const val TUTORIAL_WATCHED = "TUTORIAL_WATCHED"

    override fun getSettingName(): String = APP_SESSION



    fun setDeviceUID(value: String, context: Context) = setValue(UID, value, context)
    fun getDeviceUID(context: Context) = getSharedPreferences(context).getString(UID, "") ?: ""

    fun setUID(value: String, context: Context) = setValue(UID, value, context)
    fun getUID(context: Context) = getSharedPreferences(context).getString(UID, "")

    fun setRCX(value: String, context: Context) = setValue(RCX, value, context)
    fun getRCX(context: Context) = getSharedPreferences(context).getString(RCX, "")


    fun setRepID(value: Int, context: Context) = setValue(REPID, value, context, ValueType.INT)
    fun getRepID(context: Context) = getSharedPreferences(context).getInt(REPID, 0)


    fun setToken(value: String, context: Context) = setValue(IDTOKEN, value, context)
    fun getToken(context: Context) = getSharedPreferences(context).getString(IDTOKEN, "")

    fun setQuizzesId(value: String, context: Context) = setValue(QUIZZES, value, context)
    fun getQuizzesId(context: Context) = getSharedPreferences(context).getString(QUIZZES, "") ?: ""

    fun setShowWelcomeAlert(value: Boolean, context: Context) = setValue(SHOW_WELCOME_ALERT, value, context, ValueType.BOOLEAN)
    fun isShowWelcomeAlert(context: Context) = getSharedPreferences(context).getBoolean(
        SHOW_WELCOME_ALERT, false)

    fun setVisitor(value: Boolean, context: Context) = setValue(IS_VISITOR, value, context, ValueType.BOOLEAN)
    fun isVisitor(context: Context) = getSharedPreferences(context).getBoolean(IS_VISITOR, false)

    fun setCountryCode(value: String, context: Context) = setValue(COUNTRY_CODE, value, context)
    fun getCountryCode(context: Context) = getSharedPreferences(context).getString(COUNTRY_CODE, "") ?: ""


    //Israel new data
    fun setTutoWatched(value: Boolean, context: Context) = setValue(TUTORIAL_WATCHED, value, context, ValueType.BOOLEAN)
    fun getTutoWatched(context: Context) = getSharedPreferences(context).getBoolean(TUTORIAL_WATCHED, false)

    private val LOGGEDIN = Pair("logged_in", false)
    var loggedIn: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = getSharedPreferences(getApp().mContext).getBoolean(LOGGEDIN.first, LOGGEDIN.second)
        // custom setter to save a preference back to preferences file
        set(value) = getSharedPreferences(getApp().mContext).edit {
            it.putBoolean(LOGGEDIN.first, value)
        }

    private val XUSER_STATUS = Pair("xuser_status", false)
    var xuser_status: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = getSharedPreferences(getApp().mContext).getBoolean(XUSER_STATUS.first, XUSER_STATUS.second)
        // custom setter to save a preference back to preferences file
        set(value) = getSharedPreferences(getApp().mContext).edit{
            it.putBoolean(XUSER_STATUS.first, value)
        }


    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }
}