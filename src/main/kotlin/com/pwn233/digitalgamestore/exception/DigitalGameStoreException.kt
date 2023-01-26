package com.pwn233.digitalgamestore.exception

import com.pwn233.digitalgamestore.common.HttpConstants
import org.springframework.http.HttpStatus

open class DigitalGameStoreException(val status: HttpConstants): IllegalStateException(status.name)
