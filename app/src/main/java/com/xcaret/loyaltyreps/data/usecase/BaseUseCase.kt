package com.xcaret.loyaltyreps.data.usecase

import com.xcaret.loyaltyreps.LoyaltyApp
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.data.room.XcaretLoyaltyDatabase
import com.xcaret.loyaltyreps.data.room.dao.BaseDao
import com.xcaret.loyaltyreps.data.repository.FirebaseDatabaseRepository


abstract class  BaseUseCase<Model>  {
    abstract val TAG: String
    val database = XcaretLoyaltyDatabase.getDatabase(getApp().mContext)
    abstract fun getDao() : BaseDao<Model>?
    abstract fun getRepository(): FirebaseDatabaseRepository<Model>?

    fun insert(list: MutableList<Model>, result: (finish: List<Long>) -> Unit){
        getDao()?.insert(list, result)
    }

    fun getFromFirebase(listener: FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<Model>) {
        getRepository()?.addListener(listener)
    }

    fun getFromSingleFirebase(listener: FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<Model>){
        getRepository()?.addSingleListener(listener)
    }
}
