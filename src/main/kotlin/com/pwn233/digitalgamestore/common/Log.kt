package com.pwn233.digitalgamestore.common

import org.apache.logging.log4j.ThreadContext
import java.util.*

object Log {

    private const val LOG_ID = "trans_id"

    fun register(logId: String? = null): String {
        val generateLogId = if (logId.isNullOrBlank()){
            "DGS-${UUID.randomUUID().toString().replace("-", "")}"
        } else logId
        ThreadContext.put(LOG_ID, generateLogId)
        return generateLogId
    }

    fun get() = ThreadContext.get(LOG_ID) ?: register()
}