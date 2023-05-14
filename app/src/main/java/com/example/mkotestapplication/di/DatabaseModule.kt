package com.example.mkotestapplication.di

import android.content.Context
import com.example.mkotestapplication.repo.UserDatabaseRepository
import com.example.mkotestapplication.repo.UserDatabaseRepositoryImpl
import com.example.mkotestapplication.room.UserDao
import com.example.mkotestapplication.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase = UserDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.userDao()

    @Provides
    @Singleton
    fun provideUserDatabaseRepository(userDao: UserDao): UserDatabaseRepository =
        UserDatabaseRepositoryImpl(userDao)

}