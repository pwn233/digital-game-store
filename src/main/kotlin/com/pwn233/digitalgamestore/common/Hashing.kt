package com.pwn233.digitalgamestore.common

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

object Hashing {

    fun digest(content: String?): String? {
        return when(content.isNullOrBlank()) {
            true -> null
            else -> Hashing.sha256().hashString(content, StandardCharsets.UTF_8).toString()
        }
    }

}