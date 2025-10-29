package com.hybridge.camvvmpizza.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hybridge.camvvmpizza.data.local.dao.CartDao
import com.hybridge.camvvmpizza.data.local.entity.CartItemEntity


@Database(entities = [CartItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pizzeria-db"
                ).build().also { INSTANCE = it }
            }
    }
}