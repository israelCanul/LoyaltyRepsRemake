package com.xcaret.loyaltyreps.data.api

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ComplimentariesAPI {
    @POST("complimentaries/")
    fun fetchComplimentaries(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<ComplimentariesResponse>
}
data class ComplimentariesResponse(
    var value: List<Complimentary> = listOf(),
    var statusCode: Int
)
data class Complimentary(
    var noTarjeta: String?,
    var nombreAfiliado: String?,
    var tarjetaEspecial: String?,
    var parque: String?,
    var idServicio: String?,
    var servicio: String?,
    var noPaxBeneficio: Int,
    var noPaxUtilizado: Int,
    var noPaxPorUtilizar: Int,
    var image: String?,
    var name: String?,
    var phone: String?,
    var action: String?,
    var infants: Boolean,
    var note: String?,
    var order: Int
)
