package com.pwn233.digitalgamestore.config

import com.pwn233.digitalgamestore.controller.BasicResponse
import com.pwn233.digitalgamestore.exception.DigitalGameStoreException
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class DigitalGameStoreExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [DigitalGameStoreException::class])
    protected fun handle(ex: DigitalGameStoreException, request: WebRequest): ResponseEntity<Any> {
        val response = handleExceptionInternal(
            ex,
            BasicResponse.error(ex.status),
            HttpHeaders(),
            ex.status.httpStatus,
            request
        )
        println("ex : "+ex)
        println("response : "+response)
//        log.error(CONTROLLER_EXCEPTION_LOG_MESSAGE, this.javaClass.simpleName, Correlation.get(), ex)
//        log.error(CONTROLLER_RESPONSE_LOG_MESSAGE, this.javaClass.simpleName, Correlation.get(), response)
        return response
    }
}