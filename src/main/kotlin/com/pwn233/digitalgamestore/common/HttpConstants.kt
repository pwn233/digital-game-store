package com.pwn233.digitalgamestore.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class HttpConstants(val httpStatus: HttpStatus, val code: String, var description: String, var title: String? = null, var message: String? = null) {
    SUCCESS(OK, "0", "success"),
    CREATE_SUCCESS(CREATED, "0", "success"),
    NO_RESPONSE_BODY(NO_CONTENT, "0", "no content",),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10001", "internal server error", "something went wrong", "please wait a moment and then try again"),
}