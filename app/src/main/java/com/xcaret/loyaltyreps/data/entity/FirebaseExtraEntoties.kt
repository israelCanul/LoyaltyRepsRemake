package com.xcaret.loyaltyreps.data.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ParamSettingEntity(
    val code: String? = null,
    val value: String? = null
)
