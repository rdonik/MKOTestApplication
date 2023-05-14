package com.example.mkotestapplication.room

import androidx.room.*
import com.example.mkotestapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun loadUser(): Flow<List<User>>

    fun getUserDistinctUntilChanged() =
        loadUser().distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}