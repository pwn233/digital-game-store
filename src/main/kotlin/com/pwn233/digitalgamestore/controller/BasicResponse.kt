package com.pwn233.digitalgamestore.controller

import com.pwn233.digitalgamestore.common.HttpConstants
import io.swagger.annotations.ApiModelProperty

data class BasicResponse<T>(val status: ResponseStatus, val data: T? = null) {
    companion object {
        fun error(status: ResponseStatus) = BasicResponse<Any>(ResponseStatus.error(status))
        fun error(status: HttpConstants) = BasicResponse<Any>(ResponseStatus.error(status))
        fun success() = BasicResponse<Unit>(ResponseStatus.success())
        fun success(status: HttpConstants) = BasicResponse<Any>(ResponseStatus.success(status))
        fun <T> success(data: T? = null) = BasicResponse(ResponseStatus.success(), data)
        fun <T> error(status: HttpConstants,data: T? = null) = BasicResponse(ResponseStatus.error(status), data)
    }
}
data class ResponseStatus(
    // for display on swagger
    @ApiModelProperty(example = "0", value = "developer debug status code", required = true)
    val code: String,
    @ApiModelProperty(example = "success", value = "developer debug message", required = true)
    val description: String,
    @ApiModelProperty(example = "title to display to end user")
    val title: String? = null,
    @ApiModelProperty(example = "description to display to end user")
    val message: String? = null
) {
    companion object {
        fun success() = ResponseStatus(HttpConstants.SUCCESS.code, HttpConstants.SUCCESS.description, null, null)
        fun success(status: HttpConstants) = ResponseStatus(status.code, status.description)
        fun error(status: HttpConstants) = ResponseStatus(status.code, status.description, status.title, status.message)
        fun error(status: ResponseStatus) = ResponseStatus(status.code, status.description, status.title, status.message)
    }
}