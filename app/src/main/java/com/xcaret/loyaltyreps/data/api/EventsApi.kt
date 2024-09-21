package com.xcaret.loyaltyreps.data.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface EventsApi {
    @GET("events/{idEvent}")
    fun fetchEventDetail(@Path("idEvent") idEvent:String, @Query("token") token: String): Call<Event>
}


data class Event(
    var id: String? = null,
    var name: Name? = null,
    var logo: LogoEvent? = null,
    var url: String? = "",
    var start: Time?= null,
    var end: Time? = null,
    var capacity: String? = "",
    var status: String? = null,
    var status_code: String? = null,
    var error_description: String? = null,
    var error: String? = null
)

data class LogoEvent(
    var url: String = "",
    var id: String? = null
)
data class Name(
    var text: String = "",
    var html: String = ""
)
data class Description(
    var text: String = "",
    var html: String = ""
)
data class Time(
    var timezone: String ="America/Cancun",
    var local: String ="2022-08-14T08:30:00",
    var utc: String ="2022-08-14T13:30:00Z"
)