package com.example.mkotestapplication.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

//P - request parameter
//T - response
abstract class UseCase<in P, T>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): T {
        return withContext(coroutineDispatcher) {
            performAction(parameters)
        }
    }

    protected abstract suspend fun performAction(parameters: P): T

}