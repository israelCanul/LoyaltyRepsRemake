package com.xcaret.loyaltyreps.data.api

import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface UserApi {
    @POST("authenticate/login")
    fun login(@Body params: JsonObject): Single<UserResponse>
    @GET("authenticate/recoverPassword/{cardId}")
    fun retrievePass( @Path("cardId") cardId: String): Single<Response<String>>
    @GET("api/rep/getDetalleRepById/{id}")
    fun getDetailRepById(@Header("Authorization") idToken: String, @Path("id") idRep: Int): Single<UserResponse>

    @GET("api/Files/getTarjetaImage/{rcx}")
    fun getTarjetaImageByRep(@Header("Authorization") idToken: String, @Path("rcx") rep: String): Call<ResponseBody>
}

data class UserResponse(
    var value: XUserResponse,
    var statusCode: Int
)
data class XUserResponse(
    var id: Long = 0L,
    var idRep: Int = 0,
    var nombre: String = "",
    var apellidoPaterno: String = "",
    var apellidoMaterno: String = "",
    var rcx: String = "",
    var puntosPorVentas: Int = 0,
    var puntosParaArticulos: Int = 0,
    var puntosParaBoletos: Int = 0,
    var estatus: Boolean = false,
    var intereses: String = "",
    var idEdoCivil: Int = 0,
    var estadoCivil: String = "",
    var hijos: Int = 0,
    var correo: String = "",
    var telefono: String = "",
    var fechaNacimiento: String = "",
    var idAgencia: Int = 0,
    var agencia: String = "",
    var isTopRep: Boolean = false,
    var cnPerFilCompletado: Boolean = false,
    var idMunicipioNacimiento: Int = 0,
    var municipioNacimiento: String = "",
    var cnMainQuiz: Boolean = false,
    var idEstadoNacimiento: Int = 0,
    var estadoNacimiento: String = "",
    var tokenFirebase: String = "",
    var token: String = "",
    var level: Float = 0f,
    var totalPoints: Float = 0f,
    var totalPointsArticulos: Float = 0f,
    var totalPointsBoletos: Float = 0f,
    var xuserPhoto: String? = null,
    var xuserPhotoFront: String? = null,
    var xuserPhotoBack: String? = null,
    var idEstatusArchivos: Int = 0,
    var dsEstatusArchivos: String? = null,
    var cnTarjetaActiva: Boolean = false,
    var quizzes: List<Quizz> = listOf(),
    var cnAceptaPoliticas: Boolean = false,
    var fechaAceptaPoliticas: String? = null,
    var clave:String =  ""
)
data class Quizz(
    var idQuiz: Int = 0
)