package com.pwn233.digitalgamestore.common

import java.time.Duration
import java.time.LocalDateTime

object CommonUtils {
    fun processTimeDuration(requestProcessTime: LocalDateTime): Long {
        val responseProcessTime = LocalDateTime.now();
        return Duration.between(requestProcessTime, responseProcessTime).toMillis();
    }

    fun checkRegexCharacter(regex: String, input: String) = input.matches(regex.toRegex())

}