package com.xcaret.loyaltyreps.data.repository

import com.xcaret.loyaltyreps.data.api.UserApiService
import com.xcaret.loyaltyreps.data.entity.XUser

class UserRepository: FirebaseDatabaseRepository<XUser>() {
    private val api: UserApiService = UserApiService()
    override fun getRootNode(): String = "/users"
}




