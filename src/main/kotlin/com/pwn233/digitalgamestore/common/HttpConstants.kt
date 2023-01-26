package com.pwn233.digitalgamestore.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class HttpConstants(val httpStatus: HttpStatus, val code: String, var description: String, var title: String? = null, var message: String? = null) {
    SUCCESS(OK, "0", "Success"),
    CREATE_SUCCESS(CREATED, "0", "Success"),
    NO_RESPONSE_BODY(NO_CONTENT, "0", "No Content",),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10001", "Internal Server error", "Something went wrong", "Please wait a moment and then try again"),
}