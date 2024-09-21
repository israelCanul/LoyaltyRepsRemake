package com.xcaret.loyaltyreps.data.room.dao

import android.annotation.SuppressLint
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Dao
abstract class BaseDao<Model>(private val tableName: String = ""){
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: Model): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(item: List<Model>): List<Long>
    @Update
    abstract fun update(item: Model): Int
    @Delete
    abstract fun remove(item: Model)

    @OptIn(DelicateCoroutinesApi::class)
    open fun insert(item: List<Model>?, result: (ids: List<Long>) -> Unit){
        GlobalScope.launch(Dispatchers.Default) {  // Dispatchers.Default
            val ids = mutableListOf<Long>()
            if(!item.isNullOrEmpty()) {
                truncate()
                item.let { ids.addAll(insertAll(it)) }
            }
            withContext(Dispatchers.Main) { // Dispatchers.Main
                result(ids)
            }
        }
    }

    fun removeAll(): Boolean? {
        val queryRemove = "DELETE FROM $tableName"
        return removeByQuery(SimpleSQLiteQuery(queryRemove))
    }

    fun truncate(): Boolean? {
        val queryRemove = "DELETE FROM $tableName"
        val updatePrimary = "UPDATE sqlite_sequence SET seq=0 WHERE name='$tableName'"
        removeByQuery(SimpleSQLiteQuery(queryRemove))
        return removeByQuery(SimpleSQLiteQuery(updatePrimary))
    }

    @RawQuery
    abstract fun removeByQuery(query: SupportSQLiteQuery): Boolean?

    @SuppressLint("NewApi")
    private fun getEntityClass(): String?{
        return this::class.java.genericSuperclass?.typeName
    }


}