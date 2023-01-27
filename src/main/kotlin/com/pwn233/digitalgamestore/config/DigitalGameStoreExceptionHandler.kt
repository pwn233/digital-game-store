package com.pwn233.digitalgamestore.config

import com.ascendcorp.logger.log4j.ACNLogger
import com.pwn233.digitalgamestore.common.Constants.CONTROLLER_EXCEPTION_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.Constants.CONTROLLER_RESPONSE_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.HttpConstants
import com.pwn233.digitalgamestore.common.Log
import com.pwn233.digitalgamestore.controller.BasicResponse
import com.pwn233.digitalgamestore.exception.DigitalGameStoreException
import com.pwn233.digitalgamestore.exception.DigitalGameStoreForHttpStatusException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class DigitalGameStoreExceptionHandler: ResponseEntityExceptionHandler() {
    companion object { private val log = ACNLogger.create(this::class.java) }
    @ExceptionHandler(value = [DigitalGameStoreException::class])
    protected fun handle(ex: DigitalGameStoreException, request: WebRequest): ResponseEntity<Any> {
        val response = handleExceptionInternal(
            ex,
            BasicResponse.error(ex.status),
            HttpHeaders(),
            ex.status.httpStatus,
            request
        )
        log.error(CONTROLLER_EXCEPTION_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), ex)
        log.error(CONTROLLER_RESPONSE_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), response)
        return response
    }

    @ExceptionHandler(value = [Exception::class])
    protected fun handle(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val response = handleExceptionInternal(
            ex,
            BasicResponse.error(HttpConstants.INTERNAL_SERVER_ERROR),
            HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request
        )
        log.error(CONTROLLER_EXCEPTION_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), ex)
        log.error(CONTROLLER_RESPONSE_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), response)
        return response
    }

    @ExceptionHandler(value = [DigitalGameStoreForHttpStatusException::class])
    protected fun handle(ex: DigitalGameStoreForHttpStatusException, request: WebRequest): ResponseEntity<Any> {
        val response = handleExceptionInternal(
            ex,
            null,
            HttpHeaders(),
            ex.httpStatus,
            request
        )
        log.error(CONTROLLER_EXCEPTION_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), ex)
        log.error(CONTROLLER_RESPONSE_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), response)
        return response
    }
}