package com.pwn233.digitalgamestore.controller

import com.ascendcorp.logger.log4j.ACNLogger
import com.pwn233.digitalgamestore.common.CommonUtils.processTimeDuration
import com.pwn233.digitalgamestore.common.Constants.CONTROLLER_REQUEST_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.Constants.CONTROLLER_RESPONSE_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.Constants.LOG_ID
import com.pwn233.digitalgamestore.common.Constants.URI_INTERNAL
import com.pwn233.digitalgamestore.common.Constants.URI_PRODUCT
import com.pwn233.digitalgamestore.common.Constants.URI_STORE_MANAGEMENT
import com.pwn233.digitalgamestore.common.Hashing
import com.pwn233.digitalgamestore.common.Log
import com.pwn233.digitalgamestore.config.SwaggerConfiguration.Companion.STORE_MANAGEMENT
import com.pwn233.digitalgamestore.facade.DefaultCreateProductControllerByStoreBranchId
import io.swagger.annotations.*
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@RestController
@RequestMapping("$URI_INTERNAL$URI_STORE_MANAGEMENT")
@Api(tags = [STORE_MANAGEMENT])
class CreateProductControllerByStoreBranchId(
    private val facade: DefaultCreateProductControllerByStoreBranchId
) {

    companion object { private val log = ACNLogger.create(this::class.java) }

    @PostMapping("/{storeBranchId}${URI_PRODUCT}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
        value = "Create Product",
        notes = "Possible response status codes: 0, 10001"
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = LOG_ID)
    )
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "create product success"),
        ApiResponse(code = 400, message = "field data is invalid OR product was already created."),
        ApiResponse(code = 404, message = "store branch id not found"),
        ApiResponse(code = 500, message = "internal server error")
        ]
    )
    fun createProduct(
        @RequestHeader(LOG_ID, required = false) logId: String?,
        @PathVariable("storeBranchId") storeBranchId: String,
        @RequestBody requestBody: CreateProductRequestBody
    ): BasicResponse<Any> {
        val requestProcessTime = LocalDateTime.now()
        val payload = "Headers: [LogId to ${Hashing.digest(logId)}] And Path: [storeBranchId to $storeBranchId] And " +
                "Param: [id to ${requestBody.id}, name to ${requestBody.name}, price to ${requestBody.price}, " +
                "description to ${requestBody.description}, category to ${requestBody.category}," +
                "brand to ${requestBody.brand}, barcode to ${requestBody.barcode}, " +
                "created_by to ${requestBody.createdBy}"
        log.info(CONTROLLER_REQUEST_LOG_MESSAGE, this.javaClass.simpleName, Log.register(logId), payload)
        val response = facade.process(CreateProductRequest(storeBranchId, requestBody))
        log.info(CONTROLLER_RESPONSE_LOG_MESSAGE, this.javaClass.simpleName, Log.register(logId), payload,
            processTimeDuration(requestProcessTime))
        return response
    }
}
data class CreateProductRequest(
    val storeBranchId: String,
    val requestBody: CreateProductRequestBody
)

data class CreateProductRequestBody(
    val id: String,
    val name: String,
    val price: Double,
    val description: String? = null,
    val category: String? = null,
    val brand: String? = null,
    val barcode: String? = null,
    val createdBy: String
)