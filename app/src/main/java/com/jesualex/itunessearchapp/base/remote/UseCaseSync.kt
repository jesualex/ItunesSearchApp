package com.jesualex.itunessearchapp.base.remote

/**
 * Created by jesualex on 21-03-19.
 */
abstract class UseCaseSync<T, U> {
    protected abstract fun execute(param: U?): T

    protected abstract fun execute(): T
}
