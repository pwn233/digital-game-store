package com.pwn233.digitalgamestore.facade

import com.ascendcorp.logger.log4j.ACNLogger
import com.pwn233.digitalgamestore.common.Constants.OTHER_EXCEPTION_LOG_MESSAGE
import com.pwn233.digitalgamestore.common.HttpConstants
import com.pwn233.digitalgamestore.common.Log
import com.pwn233.digitalgamestore.common.ValidateUtils.validateCreatedByNullOrBlank
import com.pwn233.digitalgamestore.common.ValidateUtils.validateIdIsNullOrBlank
import com.pwn233.digitalgamestore.common.ValidateUtils.validateProductBarcode
import com.pwn233.digitalgamestore.common.ValidateUtils.validateProductBrand
import com.pwn233.digitalgamestore.common.ValidateUtils.validateProductCategory
import com.pwn233.digitalgamestore.common.ValidateUtils.validateProductDescription
import com.pwn233.digitalgamestore.common.ValidateUtils.validateProductNameNullOrBlank
import com.pwn233.digitalgamestore.common.ValidateUtils.validateProductPriceNullOrBlank
import com.pwn233.digitalgamestore.controller.BasicResponse
import com.pwn233.digitalgamestore.controller.CreateProductRequest
import com.pwn233.digitalgamestore.controller.CreateProductRequestBody
import com.pwn233.digitalgamestore.entity.ProductEntity
import com.pwn233.digitalgamestore.exception.DigitalGameStoreException
import com.pwn233.digitalgamestore.repository.ProductRepository
import com.pwn233.digitalgamestore.repository.StoreBranchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DefaultCreateProductControllerByStoreBranchId(
    private val productRepository: ProductRepository,
    private val storeBranchRepository: StoreBranchRepository
) {
    companion object { private val log = ACNLogger.create(this::class.java) }

    fun process(request: CreateProductRequest): BasicResponse<Any> {
        try {
            val requestBody = request.requestBody
            validateRequestBody(requestBody)
            storeBranchRepository.findByIdOrNull(request.storeBranchId)
                ?: throw DigitalGameStoreException(HttpConstants.STORE_BRANCH_ID_NOT_FOUND)
            if (productRepository.findByStoreBranchIdAndNameAndDeleted(request.storeBranchId,
                    requestBody.name, false).isPresent)
                throw DigitalGameStoreException(HttpConstants.PRODUCT_IS_DUPLICATE)
            productRepository.saveAndFlush(
                ProductEntity(
                    id = requestBody.id,
                    storeBranchId = request.storeBranchId,
                    name = requestBody.name,
                    price = requestBody.price,
                    description = requestBody.description,
                    category = requestBody.category,
                    brand = requestBody.brand,
                    barcode = requestBody.barcode,
                    createdBy = requestBody.createdBy )
            )
            return BasicResponse.success(HttpConstants.CREATE_SUCCESS)
        } catch (ex: Exception) {
            log.error(OTHER_EXCEPTION_LOG_MESSAGE, this.javaClass.simpleName, Log.get(), ex.message)
            throw ex
        }
    }

    private fun validateRequestBody(requestBody: CreateProductRequestBody) {
        validateRequestBodyNullOrBlank(requestBody)
        validateProductDescription(requestBody.description)
        validateProductCategory(requestBody.category)
        validateProductBrand(requestBody.brand)
        validateProductBarcode(requestBody.barcode)
    }

    private fun validateRequestBodyNullOrBlank(requestBody: CreateProductRequestBody) {
        validateIdIsNullOrBlank(requestBody.id)
        validateProductNameNullOrBlank(requestBody.name)
        validateProductPriceNullOrBlank(requestBody.price)
        validateCreatedByNullOrBlank(requestBody.createdBy)
    }
}