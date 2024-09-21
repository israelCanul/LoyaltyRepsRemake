package com.xcaret.loyaltyreps.data.api

import com.xcaret.loyaltyreps.BuildConfig


class UserApiService(): APIService() {
    override val baseUrl: String
        get() = BuildConfig.XCARETAPI

   fun getAPiService(): UserApi = getApi().create(UserApi::class.java)
    companion object{
        val userApiService: UserApi = UserApiService().getAPiService()
    }
}
