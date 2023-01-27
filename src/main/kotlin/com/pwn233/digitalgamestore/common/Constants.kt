package com.pwn233.digitalgamestore.common

object Constants {
        const val LOG_ID = "Log-Id"
        //build version
        const val BUILD_VERSION = "version 1.0.1a"

        //handle internal
        const val URI_INTERNAL = "/api/internal"

        //handle product, promo-code, transaction
        const val URI_STORE_MANAGEMENT = "/store"

        const val CONTROLLER_REQUEST_LOG_MESSAGE = "[{} : Request] -> [Log-Id: {}, payload: {}]"
        const val CONTROLLER_RESPONSE_LOG_MESSAGE = "[{} : Response] -> [Log-Id: {}, payload: {}, process-time: {}]"
        const val CONTROLLER_EXCEPTION_LOG_MESSAGE = "[{} : Exception] -> [Log-Id: {}, payload: {}]"
        const val OTHER_EXCEPTION_LOG_MESSAGE: String = "[{} : Exception] -> [X-Correlation-Id: {}, payload: {}]"


        //handle user, membership
        const val URI_ACCOUNT_MANAGEMENT = "/account"

        //handle uri path
        const val URI_PRODUCT = "/product"
        const val URI_PROMO_CODE = "/promo-code"
        const val URI_TRANSACTION = "/transaction"
        const val URI_USER = "/user"
        const val URI_MEMBERSHIP = "/membership"
}