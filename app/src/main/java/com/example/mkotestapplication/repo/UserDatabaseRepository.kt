package com.example.mkotestapplication.repo

import com.example.mkotestapplication.model.User
import com.example.mkotestapplication.room.UserDao
import kotlinx.coroutines.flow.Flow

interface UserDatabaseRepository {

    fun getUser(): Flow<List<User>>

    suspend fun insertUser(user: User)

}


class UserDatabaseRepositoryImpl(private val userDao: UserDao) : UserDatabaseRepository {

    override fun getUser(): Flow<List<User>> = userDao.getUserDistinctUntilChanged()

    override suspend fun insertUser(user: User) = userDao.insert(user)

}