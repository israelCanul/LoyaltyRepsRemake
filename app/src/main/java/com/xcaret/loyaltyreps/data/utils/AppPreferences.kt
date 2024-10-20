package com.xcaret.loyaltyreps.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.xcaret.loyaltyreps.BuildConfig
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppPreferences {
    private const val NAME = "LoyaltyReps"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    var operativeGuideUrl = "${BuildConfig.PUNK_API_URL}documents/1/"

    //GETTERS AND SETTERS
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }
    private val XUSER_STATUS = Pair("xuser_status", false)
    var xuser_status: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(XUSER_STATUS.first, XUSER_STATUS.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit{
            it.putBoolean(XUSER_STATUS.first, value)
        }
    private val LOGGEDIN = Pair("logged_in", false)
    var loggedIn: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(LOGGEDIN.first, LOGGEDIN.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(LOGGEDIN.first, value)
        }


    @SuppressLint("SimpleDateFormat")
    fun formatStringToDate(dt: String): String {
        try {
            //val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val convertedDate: Date?
            val formattedDate: String?

            convertedDate = sdf.parse(dt)
            formattedDate = SimpleDateFormat("EEE, dd MMM yyyy").format(convertedDate!!)

            return formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(dt: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val convertedDate: Date?
            val formattedDate: String?

            convertedDate = sdf.parse(dt)
            formattedDate = SimpleDateFormat("dd MMM yyyy").format(convertedDate!!)

            formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun formatStringToDate2(dt: String): String {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            //val sdf = SimpleDateFormat("yyyy-MM-dd")
            val convertedDate: Date?
            val formattedDate: String?

            convertedDate = sdf.parse(dt)
            formattedDate = SimpleDateFormat("EEE, dd MMM yyyy").format(convertedDate!!)

            return formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun nomalDateToFormat(mDate: String) : String {
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            //val sdf = SimpleDateFormat("yyyy-MM-dd")
            val convertedDate: Date?
            val formattedDate: String?
            convertedDate = sdf.parse(mDate)
            formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(convertedDate!!)
            return formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }
    fun toastMessage(context: Context, sectionMessage: String){
        Toast.makeText(context, sectionMessage, Toast.LENGTH_SHORT).show()
    }
    fun emptyString(stringItem: String) : String {
        val newString = ""
        if (stringItem == "" || stringItem.isEmpty()){
            return newString
        }
        return stringItem
    }
}