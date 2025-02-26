package com.hussein.androidprojectstandard.domain.base

import com.hussein.androidprojectstandard.utils.UIText
import com.hussein.androidprojectstandard.domain.base.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<T> {

    inline fun execute(crossinline request: suspend () -> Result<T>) = flow<Result<T>> {
        emit(Result.loading())

        emit(request())
    }.catch { throwable ->
        emit(
            Result.failure(
                message = if (throwable.message != null) {
                    UIText.Hardcoded(throwable.message!!)
                } else {
                    null
                }
            )
        )
    }
}