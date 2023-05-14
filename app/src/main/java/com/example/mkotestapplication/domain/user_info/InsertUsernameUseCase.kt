package com.example.mkotestapplication.domain.user_info

import com.example.mkotestapplication.domain.UseCase
import com.example.mkotestapplication.model.User
import com.example.mkotestapplication.repo.UserDatabaseRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class InsertUsernameUseCase @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository
) : UseCase<User, Unit>(Dispatchers.IO) {

    override suspend fun performAction(parameters: User) {
        userDatabaseRepository.insertUser(parameters)
    }
}