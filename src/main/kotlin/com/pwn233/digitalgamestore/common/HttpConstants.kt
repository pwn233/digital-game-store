package com.pwn233.digitalgamestore.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class HttpConstants(val httpStatus: HttpStatus, val code: String, var description: String, var title: String? = null, var message: String? = null) {
    SUCCESS(OK, "0", "success"),
    CREATE_SUCCESS(CREATED, "0", "success"),
    NO_RESPONSE_BODY(NO_CONTENT, "0", "no content"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10001", "internal server error", "something went wrong", "please wait a moment and then try again"),
    REQUIRED_DATA_IS_INVALID(BAD_REQUEST, "10002", "", "unable to complete transaction", ""),
    STORE_BRANCH_ID_NOT_FOUND(NOT_FOUND, "10003","store branch id not found", "something went wrong", "store branch id not found"),
    PRODUCT_IS_DUPLICATE(BAD_REQUEST, "10004","product is duplicated", "unable to complete transaction", "product is duplicated"),
}