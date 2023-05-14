package com.example.mkotestapplication.domain.user_info

import com.example.mkotestapplication.model.User
import com.example.mkotestapplication.repo.UserDatabaseRepository
import kotlinx.coroutines.flow.Flow
import com.example.mkotestapplication.domain.FlowUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(
    private val userDataBaseRepository: UserDatabaseRepository
) : FlowUseCase<Unit, List<User>>() {

    override fun performAction(parameters: Unit): Flow<List<User>> {
        return userDataBaseRepository.getUser()
    }


}