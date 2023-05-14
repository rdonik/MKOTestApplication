package com.example.mkotestapplication.domain

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in P, T> {

    operator fun invoke(parameters: P): Flow<T> {
        return performAction(parameters)
    }

    protected abstract fun performAction(parameters: P): Flow<T>

}