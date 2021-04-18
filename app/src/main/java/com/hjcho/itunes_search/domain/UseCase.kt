package com.hjcho.itunes_search.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


abstract class UseCase<in P, R>(val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend operator fun invoke(parameters: P): Result<R> {
        return withContext(coroutineDispatcher) {
            execute(parameters)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun CoroutineScope.execute(parameters: P): Result<R>
}

abstract class CompletableUseCase<in P, R>(val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend operator fun invoke(parameters: P): Result.Success<R> {
        return withContext(coroutineDispatcher) {
            Result.Success(execute(parameters))
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun CoroutineScope.execute(parameters: P): R
}

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(parameters: P): Flow<R> {
        return execute(parameters).flowOn(coroutineDispatcher)
    }

    abstract suspend fun execute(parameters: P): Flow<R>
}
