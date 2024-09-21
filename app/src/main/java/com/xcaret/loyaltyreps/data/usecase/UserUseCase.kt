package com.xcaret.loyaltyreps.data.usecase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

import com.google.gson.JsonObject
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.Quizz
import com.xcaret.loyaltyreps.data.api.UserApiService.Companion.userApiService
import com.xcaret.loyaltyreps.data.api.UserResponse
import com.xcaret.loyaltyreps.data.api.XUserResponse
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.repository.FirebaseDatabaseRepository
import com.xcaret.loyaltyreps.data.repository.UserRepository
import com.xcaret.loyaltyreps.data.room.dao.XUserDao
import com.xcaret.loyaltyreps.data.utils.Session

import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.util.*

class UserUseCase: BaseUseCase<XUser>() {
    private val dao = database.UserDao()
    private val repository by lazy { UserRepository() }
    override val TAG: String
        get() = "UserUseCase"


    override fun getDao(): XUserDao =  dao
    override fun getRepository(): FirebaseDatabaseRepository<XUser> = repository
    fun getCurrent(): XUser? {
        return getDao().getCurrentUser()
    }
    fun retrievePassword(rcx: String,isSuccess: (success: String?,error: String?) -> Unit){
        Log.i("API", "$rcx")
        userApiService.retrievePass(rcx)
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.computation())
            .subscribeWith(object : DisposableSingleObserver<Response<String>>() {

                override fun onSuccess(value: Response<String>) {
                    if(value?.code() == 200 || value?.code() == 201){
                        isSuccess( "Success",null)
                    }else{
                        isSuccess(null,"Error ")
                    }
                }

                override fun onError(e: Throwable) {
                    isSuccess(null,  e?.message)
                }
            })
    }
    fun login(email: String, password: String,isSuccess: (user: XUser?, error: Throwable?) -> Unit){
        val jsonObject = JsonObject()
        jsonObject.addProperty("username", email.uppercase())
        jsonObject.addProperty("password", password)
        Log.i("API","$jsonObject" )
        userApiService.login(jsonObject)
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.computation())
            .subscribeWith(object : DisposableSingleObserver<UserResponse>() {
                override fun onSuccess(login: UserResponse) {
                    Log.i("API","$login " )
                    Session.setToken(login.value.token,getApp().mContext) // se guarda el id del rep
                    Session.setRepID( login.value.idRep,getApp().mContext) // se guarda el id del rep
                    isSuccess(getXUser(login.value, email), null)
                }
                override fun onError(e: Throwable) {
                    Log.i("API","${(e as HttpException).code()} codigo, res = ${(e as HttpException).message}" ) // Obtenemos el codigo de estatus del error
                    isSuccess(null, e)
                }
            })
    }
    fun loadUserDataFromServerApi(idRep:Int, isSuccess: (user: XUser?, error: Throwable?) -> Unit){
        userApiService.getDetailRepById("Bearer ${Session.getToken(getApp().mContext)}",idRep)
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.computation())
            .subscribeWith(object : DisposableSingleObserver<UserResponse>() {
                override fun onSuccess(login: UserResponse) {
                    isSuccess(getXUser(login.value), null)
                }
                override fun onError(e: Throwable) {
                    isSuccess(null, e)
                }
            })
    }
    fun getCardImageRep(result: (user: Bitmap?, error: String?) -> Unit){
        userApiService.getTarjetaImageByRep("Bearer ${Session.getToken(getApp().mContext)}", Session.getRCX(getApp().mContext)!!)

            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    with(response) {
                        Log.i("API", "$this")
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 400..599 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> {
                                val bmp: Bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
                                result(bmp, null)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun getXUser(response: XUserResponse, email: String? = null): XUser {
        var currentUser = XUser()
        currentUser.idRep = response.idRep
        currentUser.nombre = response.nombre
        currentUser.apellidoPaterno = response.apellidoPaterno
        currentUser.apellidoMaterno = response.apellidoMaterno
        currentUser.rcx = email ?: response.rcx
        currentUser.puntosPorVentas = response.puntosPorVentas
        currentUser.puntosParaArticulos = response.puntosParaArticulos
        currentUser.puntosParaBoletos = response.puntosParaBoletos
        currentUser.estatus = response.estatus
        currentUser.intereses = response.intereses
        currentUser.idEdoCivil = response.idEdoCivil
        currentUser.estadoCivil = response.estadoCivil
        currentUser.hijos = response.hijos
        currentUser.correo = response.correo
        currentUser.telefono = response.telefono
        currentUser.fechaNacimiento = response.fechaNacimiento
        currentUser.idAgencia = response.idAgencia
        currentUser.agencia = response.agencia
        currentUser.isTopRep = response.isTopRep
        currentUser.cnPerFilCompletado = response.cnPerFilCompletado
        currentUser.idMunicipioNacimiento = response.idMunicipioNacimiento
        currentUser.municipioNacimiento = response.municipioNacimiento
        currentUser.idEstadoNacimiento = response.idEstadoNacimiento
        currentUser.estadoNacimiento = response.estadoNacimiento
        currentUser.tokenFirebase = response.tokenFirebase
        currentUser.idEstatusArchivos = response.idEstatusArchivos
        currentUser.dsEstatusArchivos = response.dsEstatusArchivos
        currentUser.cnTarjetaActiva = response.cnTarjetaActiva
        currentUser.cnMainQuiz = response.cnMainQuiz
        currentUser.cnAceptaPoliticas = response.cnAceptaPoliticas
        currentUser.fechaAceptaPoliticas = response.fechaAceptaPoliticas
        if (response.quizzes.isNotEmpty()) {
            val mids: ArrayList<Int> = ArrayList()
            for (item in 0 until response.quizzes.size) {
                val quizitem: Quizz = response.quizzes[item]
                mids.add(quizitem.idQuiz)
            }
            val intIds = "${mids}".replace("[", "")
            val intIds2 = intIds.replace("]", "")
            val finalids = intIds2.replace(" ", "")
            currentUser.quizzes = finalids
        } else {
            currentUser.quizzes = ""
        }
        return currentUser
    }
}





