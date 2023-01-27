package com.pwn233.digitalgamestore.controller

import com.ascendcorp.logger.log4j.ACNLogger
import com.pwn233.digitalgamestore.common.CommonUtils.processTimeDuration
import com.pwn233.digitalgamestore.common.Constants.CONTROLLER_REQUEST_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.Constants.CONTROLLER_RESPONSE_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.Constants.LOG_ID
import com.pwn233.digitalgamestore.common.Constants.URI_INTERNAL
import com.pwn233.digitalgamestore.common.Constants.URI_PRODUCT
import com.pwn233.digitalgamestore.common.Constants.URI_STORE_MANAGEMENT
import com.pwn233.digitalgamestore.common.Hashing.digest
import com.pwn233.digitalgamestore.common.Log
import com.pwn233.digitalgamestore.config.SwaggerConfiguration.Companion.STORE_MANAGEMENT
import com.pwn233.digitalgamestore.facade.DefaultGetProductFacade
import io.swagger.annotations.*
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@RestController
@RequestMapping("$URI_INTERNAL$URI_STORE_MANAGEMENT")
@Api(tags = [STORE_MANAGEMENT])
class GetProductController(
    private val facade: DefaultGetProductFacade
) {
    @GetMapping("/{storeBranchId}$URI_PRODUCT")
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(
            value = "Get Product API",
            notes = "Possible response status codes: 0, 10001"
        )
        @ApiImplicitParams(
            ApiImplicitParam(name = LOG_ID),
        )
        @ApiResponses(
            value = [
                ApiResponse(code = 200, message = "get product success"),
                ApiResponse(code = 204, message = "no content"),
                ApiResponse(code = 401, message = "unauthorized"),
                ApiResponse(code = 403, message = "forbidden"),
                ApiResponse(code = 404, message = "not found"),
                ApiResponse(code = 500, message = "internal server error")
            ]
        )
        fun getProduct(
            @RequestHeader(LOG_ID, required = false) logId: String?,
            @PathVariable("storeBranchId") storeBranchId: String,
            @RequestParam(name = "name", required = false) name: String?,
            @RequestParam(name = "price", required = false) price: Double?,
            @RequestParam(name = "description", required = false) description: String?,
            @RequestParam(name = "category", required = false) category: String?,
            @RequestParam(name = "brand", required = false) brand: String?,
            @RequestParam(name = "barcode", required = false) barcode: String?,
            @RequestParam(name = "page_no", required = false, defaultValue = "1") pageNo: Int,
            @RequestParam(name = "page_size",required = false, defaultValue = "10") pageSize: Int
        ): BasicResponse<Page<ProductResponse>> {
            val requestProcessTime = LocalDateTime.now()
            val payload = "Headers: [LogId to ${digest(logId)}] And " +
                    "Path: [storeBranchId to $storeBranchId] And " +
                    "Param: [name to $name, description to $description, category to $category," +
                    "brand to $brand, barcode to $barcode, pageNo to $pageNo, pageSize to $pageSize]"
            log.info(CONTROLLER_REQUEST_LOG_MESSAGE, this.javaClass.simpleName, Log.register(logId), payload)
            val response = BasicResponse.success(facade.process(
                ProductRequest(storeBranchId, name, price, description, category, brand, barcode, pageNo, pageSize)))
            log.info(CONTROLLER_RESPONSE_LOG_MESSAGE, this.javaClass.simpleName, Log.get(),
                response, processTimeDuration(requestProcessTime))
            return response
        }
    companion object {
        private val log = ACNLogger.create(this::class.java)
    }
}

data class ProductRequest(
    val storeBranchId: String,
    val name: String? = null,
    val price: Double? = null,
    val description: String? = null,
    val category: String? = null,
    val brand: String? = null,
    val barcode: String? = null,
    val pageNo: Int,
    val pageSize: Int
)


data class ProductResponse(
    val name: String? = null,
    val price: Double,
    val description: String? = null,
    val category: String? = null,
    val brand: String? = null,
    val barcode: String? = null,
    val numberOfPromoCode: Int? = 0,
    val deleted: Boolean
)