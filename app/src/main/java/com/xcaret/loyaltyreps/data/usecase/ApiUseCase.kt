package com.xcaret.loyaltyreps.data.usecase

import android.util.Log
import com.google.gson.JsonObject
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.ApiEventBrite
import com.xcaret.loyaltyreps.data.api.ApiLoyaltyService
import com.xcaret.loyaltyreps.data.api.ApiXcaretService
import com.xcaret.loyaltyreps.data.api.ArticuloResponse
import com.xcaret.loyaltyreps.data.api.CajearArticuloValue
import com.xcaret.loyaltyreps.data.api.Category
import com.xcaret.loyaltyreps.data.api.ComplimentariesAPI
import com.xcaret.loyaltyreps.data.api.ComplimentariesResponse
import com.xcaret.loyaltyreps.data.api.Complimentary
import com.xcaret.loyaltyreps.data.api.DetailAddPoints
import com.xcaret.loyaltyreps.data.api.Event
import com.xcaret.loyaltyreps.data.api.EventsApi
import com.xcaret.loyaltyreps.data.api.GeneralAPI
import com.xcaret.loyaltyreps.data.api.ResponseAddPointsUser
import com.xcaret.loyaltyreps.data.api.ResponseCajearArticulo
import com.xcaret.loyaltyreps.data.api.ResponseCategories
import com.xcaret.loyaltyreps.data.api.ResponseFaqs
import com.xcaret.loyaltyreps.data.api.ResponseNewsFeed
import com.xcaret.loyaltyreps.data.api.ResponseQuiz
import com.xcaret.loyaltyreps.data.api.StoreArticlesAPI
import com.xcaret.loyaltyreps.data.api.StoreRecord
import com.xcaret.loyaltyreps.data.api.StoreRecordResponse
import com.xcaret.loyaltyreps.data.api.TrainingAPI
import com.xcaret.loyaltyreps.data.api.TrainingSection
import com.xcaret.loyaltyreps.data.api.UserPointsAsignedResponse
import com.xcaret.loyaltyreps.data.api.UserPointsAsinged
import com.xcaret.loyaltyreps.data.api.ValueStore
import com.xcaret.loyaltyreps.data.api.VideoTraining
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.data.config.Settings
import com.xcaret.loyaltyreps.data.config.Settings.idUsuarioApi
import com.xcaret.loyaltyreps.data.utils.Session

import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.create

class ApiUseCase{
    val TAG = "ApiUseCase"
    /**
     * FAQ´s
     * **/
    fun getFaqs(result: (succes: List<ResponseFaqs>?, error: String?) -> Unit){
        val apiParks = ApiLoyaltyService.apiService.create(GeneralAPI::class.java)
        apiParks.getFaqsApi("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}")
            .enqueue(object : Callback<List<ResponseFaqs>> {
                override fun onResponse(
                    call: Call<List<ResponseFaqs>>,
                    response: Response<List<ResponseFaqs>>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 400..405 ->{
                                result(null, getApp().mContext.getString(R.string.petition_error))
                            }
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<List<ResponseFaqs>>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    /**
     * NewsFeed
     * **/
    fun getNewsFeed(result: (succes: List<ResponseNewsFeed>?, error: String?) -> Unit){
        val apiParks = ApiLoyaltyService.apiService.create(TrainingAPI::class.java)
        apiParks.getNewsFeed("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}")
            .enqueue(object : Callback<List<ResponseNewsFeed>> {
                override fun onResponse(
                    call: Call<List<ResponseNewsFeed>>,
                    response: Response<List<ResponseNewsFeed>>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 ->{
                                result(null, getApp().mContext.getString(R.string.petition_error))
                            }
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<List<ResponseNewsFeed>>, t: Throwable) {
                    Log.e("API","MAIN QUIZ Error $t")
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    /**
     * PickUps
     * **/


    /**
     * Training
     * **/
    fun getVideoQuizTraining(videoId: Int,result: (succes: ResponseQuiz?, error: String?) -> Unit){
        val apiParks = ApiLoyaltyService.apiService.create(TrainingAPI::class.java)
        apiParks.fetchVideoQuizTraining ("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}", videoId)
            .enqueue(object : Callback<ResponseQuiz> {
                override fun onResponse(
                    call: Call<ResponseQuiz>,
                    response: Response<ResponseQuiz>
                ) {
                    with(response) {

                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 ->{
                                Log.e("API","MAIN QUIZ Response  $response")
                                result(null, getApp().mContext.getString(R.string.petition_error))
                            }
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseQuiz>, t: Throwable) {
                    Log.e("API","MAIN QUIZ Error $t")
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun getMainQuiz(result: (succes: ResponseQuiz?, error: String?) -> Unit){
        val apiParks = ApiLoyaltyService.apiService.create(TrainingAPI::class.java)
        apiParks.getMainQuiz ("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}")
            .enqueue(object : Callback<ResponseQuiz> {
                override fun onResponse(
                    call: Call<ResponseQuiz>,
                    response: Response<ResponseQuiz>
                ) {
                    with(response) {

                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 ->{
                                Log.e("API","MAIN QUIZ Response  $response")
                                result(null, getApp().mContext.getString(R.string.petition_error))
                            }
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseQuiz>, t: Throwable) {
                    Log.e("API","MAIN QUIZ Error $t")
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun updateMainUserQuiz(result: (succes: Boolean?, error: String?) -> Unit){
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("idRep", "${Session.getRepID(getApp().mContext)}")
            jsonObject.addProperty("cnMainQuizDone", true)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val apiQuizz =  ApiXcaretService.apiService.create(TrainingAPI::class.java)
        apiQuizz.updateMainQuizRep("bearer ${Session.getToken(getApp().mContext)}",jsonObject)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..599 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> {
                                if(response.body() == true){
                                    result(response.body(), null)
                                }else{
                                    result(false, "error on save")
                                }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun addPointToUser(wallet: Int, points: Int, comentario: String, result: (succes: DetailAddPoints?, error: String?) -> Unit){
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("idRep", "${Session.getRepID(getApp().mContext)}")
            jsonObject.addProperty("idMonedero", wallet)
            jsonObject.addProperty("idUsuario",
                Settings.getParam(Settings.idUsuarioApi,getApp().mContext)
            )
            jsonObject.addProperty("puntos", points)
            jsonObject.addProperty("comentario", "Quiz - $comentario")
            jsonObject.addProperty("ip", Settings.xip)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val apiParks =  ApiXcaretService.apiService.create(TrainingAPI::class.java)
        apiParks.addPointsToUser("bearer ${Session.getToken(getApp().mContext)}",jsonObject)
            .enqueue(object : Callback<ResponseAddPointsUser> {
                override fun onResponse(
                    call: Call<ResponseAddPointsUser>,
                    response: Response<ResponseAddPointsUser>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..599 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> {
                              if(response.body()!!.value.error == 0){
                                  result(response.body()!!.value, null)
                              }else{
                                  result(null, response.body()!!.value.detalle)
                              }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseAddPointsUser>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun addUserQuiz( idQuiz: Int,result: (succes: Boolean?, error: String?) -> Unit){
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("idRep", "${Session.getRepID(getApp().mContext)}")
            jsonObject.addProperty("idQuiz", idQuiz)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val apiParks =  ApiXcaretService.apiService.create(TrainingAPI::class.java)
        apiParks.addUserQuiz("bearer ${Session.getToken(getApp().mContext)}",jsonObject)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> {
                             if(response.body() == true){
                                 result(response.body(), null)
                             }else{
                                 result(false, "error on save")
                             }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun fetchTrainingDetail(idPark: String,result: (succes: TrainingSection?, error: String?) -> Unit){
        val apiParks = ApiLoyaltyService.apiService.create(TrainingAPI::class.java)
        apiParks.fetchParksTrainingDetail ("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}",idPark)
            .enqueue(object : Callback<TrainingSection> {
                override fun onResponse(
                    call: Call<TrainingSection>,
                    response: Response<TrainingSection>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<TrainingSection>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun fetchTrainingVideos(result: (succes: List<VideoTraining>?, error: String?) -> Unit){
        val apiParks = ApiLoyaltyService.apiService.create(TrainingAPI::class.java)
        apiParks.fetchVideosTraining("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}")
            .enqueue(object : Callback<List<VideoTraining>> {
                override fun onResponse(
                    call: Call<List<VideoTraining>>,
                    response: Response<List<VideoTraining>>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<List<VideoTraining>>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })
    }
    fun fetchParks(result: (succes: List<XPark>?, error: String?) -> Unit){
        Log.i("Trainin", Settings.getParam(Settings.PUNK_API_TOKEN,getApp().mContext))
        val apiParks = ApiLoyaltyService.apiService.create(TrainingAPI::class.java)
        apiParks.fetchParksTraining("Token ${
            Settings.getParam(
                Settings.PUNK_API_TOKEN,
                getApp().mContext)}")
            .enqueue(object : Callback<List<XPark>> {
                override fun onResponse(
                    call: Call<List<XPark>>,
                    response: Response<List<XPark>>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> result(response.body(), null)
                        }
                    }
                }
                override fun onFailure(call: Call<List<XPark>>, t: Throwable) {
                    result(null, "${getApp().mContext.getString(R.string.petition_error)} ss ${t.message} $t")
                }
            })
    }
    /**
     * FOR STORE SECTION
     *TODO:
     * canjearArticulo() aun no esta probado
     *
     *
     *
     * **/
    fun canjearArticulo(numBoletos:Int = 0,idarticulo: Int,result: (succes: CajearArticuloValue?, error: String?) -> Unit){
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("idRep", "${Session.getRepID(getApp().mContext)}")
            jsonObject.addProperty("idarticulo", idarticulo)
            jsonObject.addProperty("numBoletosSolicitados", numBoletos)
            jsonObject.addProperty("ip", Settings.xip)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val storeCategories = ApiXcaretService.apiService.create(StoreArticlesAPI::class.java)

        storeCategories.canjearArticulo(
            "Bearer ${Session.getToken(getApp().mContext)}",
            jsonObject
        )
            .enqueue(object : Callback<ResponseCajearArticulo> {
                override fun onResponse(
                    call: Call<ResponseCajearArticulo>,
                    response: Response<ResponseCajearArticulo>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> {
                                if(response.body()!!.value.error == 0){
                                    result(response.body()!!.value, null)
                                }else{
                                    result(null, response.body()!!.value.detalle)
                                }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseCajearArticulo>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })

    }
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchStoreArticles(result: (succes: ValueStore?, error: String?) -> Unit){
        val storeCategories = ApiXcaretService.apiService.create(StoreArticlesAPI::class.java)

        storeCategories.fetchArticles(
            "Bearer ${Session.getToken(getApp().mContext)}",
            Session.getRepID(getApp().mContext)
        )
        .enqueue(object : Callback<ArticuloResponse> {
            override fun onResponse(
                call: Call<ArticuloResponse>,
                response: Response<ArticuloResponse>
            ) {
                with(response){
                    when(code()){
                        204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                        in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                        200 -> {
                            result(response.body()!!.value, null)
                        }
                    }
                }
            }
            override fun onFailure(p0: Call<ArticuloResponse>, p1: Throwable) {
                result(null, getApp().mContext.getString(R.string.petition_error))
            }
        })

    }
    fun fetchCategories(result: (succes: List<Category>?, error: String?) -> Unit){

        val storeCategories = ApiXcaretService.apiService.create(StoreArticlesAPI::class.java)
        storeCategories.fetchCategoriesArticle(
            "Bearer ${Session.getToken(getApp().mContext)}"
        )
        .enqueue(object : Callback<ResponseCategories> {
            override fun onResponse(
                call: Call<ResponseCategories>,
                response: Response<ResponseCategories>
            ) {
                with(response){
                    when(code()){
                        204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                        in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                        200 -> {
                            result(response.body()!!.value, null)
                        }
                    }
                }
            }
            override fun onFailure(p0: Call<ResponseCategories>, p1: Throwable) {
                result(null, getApp().mContext.getString(R.string.petition_error))
            }
        })

    }
    /**
     * REPORTES
     * **/
    fun fetchTopTepAsignacion(result: (succes: List<UserPointsAsinged>?, error: String?) -> Unit){

            val jsonObject = JsonObject()
            try {
                jsonObject.addProperty("idRep", Session.getRepID(getApp().mContext))
                jsonObject.addProperty("idUsuario",Settings.getParam(idUsuarioApi, getApp().mContext))
                jsonObject.addProperty("ip", "")
                Log.i("API idUsuario", Settings.getParam(idUsuarioApi, getApp().mContext) + " $jsonObject ")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val topTenPoints = ApiXcaretService.apiService.create(StoreArticlesAPI::class.java)
            topTenPoints.fetchUserPointsAsigned(
                "Bearer ${Session.getToken(getApp().mContext)}",
                jsonObject
            ).enqueue(object : Callback<UserPointsAsignedResponse> {
                override fun onResponse(
                    call: Call<UserPointsAsignedResponse>,
                    response: Response<UserPointsAsignedResponse>
                ) {
                    with(response) {
                        when(code()){
                            204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                            in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                            200 -> result(response.body()?.value, null)
                        }
                    }
                }
                override fun onFailure(call: Call<UserPointsAsignedResponse>, t: Throwable) {
                    result(null, getApp().mContext.getString(R.string.petition_error))
                }
            })

    }
    fun fetchStoreRecords(result: (succes: List<StoreRecord>?, error: String?) -> Unit){

        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("idRep", Session.getRepID(getApp().mContext))
            jsonObject.addProperty("idUsuario",Settings.getParam(idUsuarioApi, getApp().mContext))
            jsonObject.addProperty("ip", "")
            Log.i("API idUsuario", Settings.getParam(idUsuarioApi, getApp().mContext) + " $jsonObject ")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val storeCategories = ApiXcaretService.apiService.create(StoreArticlesAPI::class.java)
        storeCategories.fetchRecordsStore(
            "Bearer ${Session.getToken(getApp().mContext)}",
            jsonObject
        ).enqueue(object : Callback<StoreRecordResponse> {
            override fun onResponse(
                call: Call<StoreRecordResponse>,
                response: Response<StoreRecordResponse>
            ) {
                with(response) {
                    when(code()){
                        204 -> result(listOf(), " " + getApp().mContext.getString(R.string.there_is_no_records))
                        in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                        200 -> result(response.body()?.value, null)
                    }
                }
            }
            override fun onFailure(call: Call<StoreRecordResponse>, t: Throwable) {
                result(null, getApp().mContext.getString(R.string.petition_error))
            }
        })

    }

    /**
     * COMPLIMENTARIES
     * */
    fun fetchComplimentariesFromApi(result: (succes: List<Complimentary>?, error: String?) -> Unit) {

        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("card",  Session.getRCX(getApp().mContext))
            jsonObject.addProperty("token", Session.getToken(getApp().mContext))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val complimentaries = ApiLoyaltyService.apiService.create(ComplimentariesAPI::class.java)
        complimentaries.fetchComplimentaries("Token ${Settings.getParam(Settings.PUNK_API_TOKEN,getApp().mContext)}", jsonObject)
        .enqueue(object : Callback<ComplimentariesResponse> {
            override fun onResponse(
                call: Call<ComplimentariesResponse>,
                response: Response<ComplimentariesResponse>
            ) {
                with(response){
                    when(code()){
                        204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                        in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                        200 -> {
                            result(response.body()!!.value, null)
                        }
                    }
                }
            }
            override fun onFailure(p0: Call<ComplimentariesResponse>, p1: Throwable) {
                result(null, getApp().mContext.getString(R.string.petition_error))
            }
        })

    }

    /**
     * Events API
     *
     * */
    fun fetchEventDetail(eventID: String, token: String,  result: (succes: Event?, error: String?) -> Unit){

        val events = ApiEventBrite.apiService.create(EventsApi::class.java)

        events.fetchEventDetail(eventID,token)
        .enqueue(object : Callback<Event> {
            override fun onResponse(
                call: Call<Event>,
                response: Response<Event>
            ) {
                with(response){
                    when(code()){
                        204 -> result(null, getApp().mContext.getString(R.string.there_is_no_records))
                        in 401..405 -> result(null, getApp().mContext.getString(R.string.petition_error))
                        200 -> {
                            result(response.body(), null)
                        }
                    }
                }
            }
            override fun onFailure(p0: Call<Event>, p1: Throwable) {
                result(null, getApp().mContext.getString(R.string.petition_error))
            }
        })

    }
}