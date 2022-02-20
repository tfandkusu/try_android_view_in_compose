package com.tfandkusu.androidview.error

/**
 * Server error
 */
class ServerErrorException(val code: Int, val httpMessage: String) : Exception()
