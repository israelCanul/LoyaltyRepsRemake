package com.xcaret.loyaltyreps.data.api

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface StoreArticlesAPI {
    @POST("Articulo/canjear")
    fun canjearArticulo(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<ResponseCajearArticulo>
    @GET("api/Articulo/{idRep}")
    fun fetchArticles(@Header("Authorization") idToken: String,@Path("idRep") idRep:Int): Call<ArticuloResponse>
    @GET("api/Articulo/getCategoriasArticulo")
    fun fetchCategoriesArticle(@Header("Authorization") idToken: String): Call<ResponseCategories>
    @POST("api/reporte/getOperacionesCanje2")
    fun fetchRecordsStore(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<StoreRecordResponse>
    @POST("api/reporte/getTopTenAsignacionPuntos")
    fun fetchUserPointsAsigned(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<UserPointsAsignedResponse>
}
data class ResponseCajearArticulo(
    var value: CajearArticuloValue = CajearArticuloValue(),
    var statusCode: Int = 200
)
data class CajearArticuloValue(
    val error: Int = 0,
    val detalle: String = "",
)
data class UserPointsAsignedResponse(
    var value: List<UserPointsAsinged> = listOf(),
    var statusCode: Int
)
data class UserPointsAsinged(
    var idAsignacionPuntos: Int? = null,
    var idRep: Int? = null,
    var idUsuario: Int? = null,
    var fecha: String = "",
    var puntos: Int = 0,
    var comentario: String = ""
)
data class StoreRecordResponse(
    var value: List<StoreRecord> = listOf(),
    var statusCode: Int
)
data class StoreRecord(
    var idOperacion: Int = 0,
    var idEdoOperacion: Int? = null,
    var fecha: String? = null,
    var mip: String = "",
    var puntos: Int = 0,
    var articulo: String? = null,
    var idEstatus: Int = 0,
    var estatus: String = "",
    var observaciones: String = ""
)
data class ArticuloResponse(
    var value: ValueStore = ValueStore(),
    var error: Int = 0,
    var detalle: String = "",
    var statusCode: Int
)
data class ValueStore(
    var articulos: List<Articulo> = listOf(),
    var error: Int = 0,
    var detalle: String = "",
)
data class Articulo(
    var id_art: Int = 0,
    var nombre: String = "",
    var idCategoriaArticulo: Int = 0,
    var clave: String = "",
    var puntos: Int = 0,
    var descripcion: String = "",
    var fechalta: String = "",
    var foto: String = "",
    var thumb: String =  "",
    var llave: String = "",
    var stock: Int = 0,
    var prodmes: Boolean = false,
    var canjeoModo: Int = 0,
    var esRifa: Boolean = false,
    var activo: Boolean = false,
    var feVigencia: String = "",
    var cnCurso: Boolean = false,
    var cnHotSale: Boolean = false,
    var isVisible: Boolean = true
)
data class ListStore(
    var listArticles : List<Articulo> = mutableListOf(),
    var listCourses : List<Articulo> = mutableListOf(),
    var listHotsale : List<Articulo> = mutableListOf(),
)

data class ResponseCategories(
    val value: List<Category> = listOf(),
    val statusCode: Int = 200
)
data class Category(
    var idCategoriaArticulo: Int? = null,
    var dsDescripcion: String = "",
    var feAlta: String = ""
)
