package com.xcaret.loyaltyreps.data.api

import com.xcaret.loyaltyreps.BuildConfig


class ApiLoyaltyService(): APIService()  {
    override val baseUrl: String
        get() = BuildConfig.PUNK_API_URL

    companion object{
        val apiService = ApiLoyaltyService().getApi()
    }
}
class ApiXcaretService(): APIService()  {
    override val baseUrl: String
        get() = BuildConfig.XCARETAPI
    companion object{
        val apiService = ApiXcaretService().getApi()
    }
}

class ApiEventBrite(): APIService()  {
    override val baseUrl: String
        get() = BuildConfig.EVENT_BRITE_URL
    companion object{
        val apiService = ApiEventBrite().getApi()
    }
}