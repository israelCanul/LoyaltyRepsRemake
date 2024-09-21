package com.xcaret.loyaltyreps.data.usecase

import android.util.Log
import com.xcaret.loyaltyreps.data.entity.XUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DownloadUseCase(private val resultListener: ResultListener) {
    val userCase : UserUseCase = UserUseCase()


    private var listaDescargas: ArrayList<String> = ArrayList<String>()
    init {


    }
    companion object{
        private val auxListUserUseCase = mutableListOf<Any>()
        fun cleanListener(){
            try {

            }catch (e: java.lang.Exception){
                e.printStackTrace()
            }
        }
    }

    private fun downloadInfo(){
        if(listaDescargas.isEmpty()){
            notifyDownload("tag: String")
        }

    }
    private fun downloadLangInfo(){
        if(listaDescargas.isEmpty()){
            notifyDownload("")
        }
    }
    private fun notifyDownload(tag: String){
        Log.i("Down-$tag", "listaDescargas : ${listaDescargas.size}")
        val tagExists: Int = listaDescargas.indexOf(tag)
        if(tagExists >= 0){
            listaDescargas.remove(tag)
        }
        Log.i("Down-$tag", "listaDescargas : ${listaDescargas.size}")
        if(listaDescargas.isEmpty()){
            resultListener.onResult(ResultDownload(true," Descargas hechas"))
        }
    }

    suspend fun getLocalUser(): XUser? {
        return withContext(Dispatchers.IO) {
            val xUser = userCase.getCurrent()
            xUser
        }
    }

    fun startDownload(whatInfo: String = TypeInfo.UserInfo as String){
        downloadLangInfo()
        downloadInfo()
    }

    enum class TypeInfo(val value: String) {
        UserInfo("UserInfo"),
    }
    interface ResultListener{
        fun onResult(result: ResultDownload)
    }
    class ResultDownload(
        val success: Boolean = false,
        val errorCode: String = ""
    )
}