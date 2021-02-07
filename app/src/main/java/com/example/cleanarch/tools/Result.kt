package com.example.cleanarch.tools

class Result<T, E> private constructor(
    private val success: ValueWrapper<T>? = null,
    private val error: ValueWrapper<E>? = null
){
    private class ValueWrapper<T>(val value: T)

    val isError = error != null

    val successResult: T
    get() = requireNotNull(success) {
        "Result was without  success"
    }.value

    val errorResult: E
    get() = requireNotNull(error) {
        "Result was without  error"
    }.value

    fun <R> mapSuccess(transformation: (T) -> R): Result<R, E> {
        return Result(
            success = success?.let { ValueWrapper(transformation(it.value)) },
            error = error
        )
    }

    fun <R> mapError(transformation: (E) -> R): Result<T, R> {
        return Result(
            success = success,
            error = error?.let { ValueWrapper(transformation(it.value)) }
        )
    }

    companion object {
        fun <T, E> success(entity: T): Result<T, E> {
            Result
            return Result(ValueWrapper(entity), null)
        }

        fun <T, E> error(error: E): Result<T, E> {
            Result
            return Result(null, ValueWrapper(error))
        }
    }
}