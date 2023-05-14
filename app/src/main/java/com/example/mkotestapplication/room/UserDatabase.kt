package com.example.mkotestapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mkotestapplication.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        var instance: UserDatabase? = null
        private const val DATABASE_NAME = "User"

        fun getInstance(context: Context): UserDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context, UserDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }

    }
}