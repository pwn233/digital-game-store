package com.pwn233.digitalgamestore.exception

import org.springframework.http.HttpStatus

open class DigitalGameStoreForHttpStatusException(val httpStatus: HttpStatus): IllegalStateException(httpStatus.name)