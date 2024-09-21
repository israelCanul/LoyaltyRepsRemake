package com.xcaret.loyaltyreps.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.xcaret.loyaltyreps.data.entity.XUser


@Dao
abstract class XUserDao: BaseDao<XUser>() {

    @Query("SELECT * FROM xuser_table WHERE id = :key")
    abstract fun get(key: Long) : XUser?
    @Query("SELECT * FROM xuser_table ORDER BY id DESC LIMIT 1")
    abstract fun getCurrentUser() : XUser?

}