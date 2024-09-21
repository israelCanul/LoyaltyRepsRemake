package com.xcaret.loyaltyreps.data.api


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface TrainingAPI {
    @GET("parks/")
    fun fetchParksTraining(@Header("Authorization") idToken: String): Call<List<XPark>>
    @GET("training_section")
    fun fetchParksTrainingDetail(@Header("Authorization") idToken: String, @Query("park_id") idPark: String): Call<TrainingSection>
    @GET("videos/")
    fun fetchVideosTraining(@Header("Authorization") idToken: String): Call<List<VideoTraining>>



    @POST("api/rep/addRepQuiz")
    fun addUserQuiz(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<Boolean>
    @POST("api/rep/asignacionPuntos")
    fun addPointsToUser(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<ResponseAddPointsUser>

    @POST("api/rep/updMainQuizRep")
    fun updateMainQuizRep(@Header("Authorization") idToken: String,@Body params: JsonObject): Call<Boolean>

    @GET("main_quiz/")
    fun getMainQuiz(@Header("Authorization") idToken: String):Call<ResponseQuiz>
    @GET("quiz")
    fun fetchVideoQuizTraining(@Header("Authorization") idToken: String, @Query("video_id") idVideo: Int ): Call<ResponseQuiz>
    @GET("newsfeed")
    fun getNewsFeed(@Header("Authorization") idToken: String): Call<List<ResponseNewsFeed>>


}
data class ResponseNewsFeed(
    val id:Int ? = null,
    val cover_img: String = "",
    val title:String = "",
    val description: String = "",
    val date:String? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)
data class ResponseQuiz(
    val id: Int? = null,
    val questions:List<Question> = listOf(),
    val wallet: Int = 0,
    val name: String = "",
    val points: Int = 0,
    val main_quiz: Boolean = false,
    val video: String? = null
)
data class Question(
    val id: Int? = null,
    val choices: List<Choice> = listOf(),
    val question: String = "",
    val quiz: Int? = null
)
data class Choice(
    val id: Int? = null,
    val option: String =  "",
    val is_correct:Boolean =  false,
    val question: Int? = null
)
data class ResponseAddPointsUser(
    var value: DetailAddPoints = DetailAddPoints(),
    var statusCode: Int = 200
)
data class DetailAddPoints(
    val error: Int = 0,
    val detalle: String = "",
    val puntosParaArticulos: Int = 0,
    val puntosParaBoletos: Int = 0
)
data class TrainingSection(
    var id: Int? = null,
    var images: List<TrainingImagesSection> = listOf(),
    val training_details: List<TrainingDetail> = listOf(),
    var description: String = "",
    val video: String = "",
    val cover_img: String = "",
    val park: Int? = null
)
data class TrainingImagesSection(
    val name: String = "",
    val image: String = "",
    val isSelected: Boolean = false
)
data class TrainingDetail(
    var id: Int? = null,
    val name: String = "",
    var description: String = "",
    var training_section: Int = 0
)
data class XPark(
    var id: Int? = null,
    val name: String = "",
    var logo: String = "",
    var color: String = "",
    var infographics: ArrayList<XParkInfographic> = arrayListOf()
)

data class XParkInfographic(
    var id: Int? = null,
    var language: String = "",
    var image: String = "",
    var park: Int? = null
)
data class VideoTraining(
    var id: Int? = null,
    val name: String = "",
    var video: String = "",
    var cover_img: String = "",
    var points: Int = 0,
    var active: Boolean = false,
    var wallet: Int = 0,
    var quiz_id: Int = 0,
    var deadline: String = "",
    var quiz_available: Boolean = false,
    var visibility: Boolean = false
)