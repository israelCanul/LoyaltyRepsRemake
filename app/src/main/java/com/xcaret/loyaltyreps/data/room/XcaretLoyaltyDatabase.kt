package com.xcaret.loyaltyreps.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xcaret.loyaltyreps.BuildConfig
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.room.dao.XUserDao

@Database(
    entities =[XUser::class],
    version = BuildConfig.VERSION_CODE,
    exportSchema = false)
abstract class XcaretLoyaltyDatabase: RoomDatabase() {
    abstract fun UserDao(): XUserDao

    companion object {
        private const val DB_NAME = "xcaret_loyalty_database"
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: XcaretLoyaltyDatabase? = null

        fun getDatabase(context: Context): XcaretLoyaltyDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    XcaretLoyaltyDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}