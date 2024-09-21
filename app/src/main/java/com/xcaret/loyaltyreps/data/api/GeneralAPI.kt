package com.xcaret.loyaltyreps.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GeneralAPI {
    @GET("faqs/")
    fun getFaqsApi(@Header("Authorization") idToken: String): Call<List<ResponseFaqs>>
}
data class ResponseFaqs(
    val id: Int? = null,
    val faqs: List<Faq> = listOf(),
    val title: String = "",
    val order: Int = 0
)
data class Faq(
    val id: Int? = null,
    val question: String = "",
    val answer: String = "",
    val faq_theme: Int = 0,
)