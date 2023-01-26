package com.pwn233.digitalgamestore.facade

import com.pwn233.digitalgamestore.common.HttpConstants
import com.pwn233.digitalgamestore.controller.ProductRequest
import com.pwn233.digitalgamestore.controller.ProductResponse
import com.pwn233.digitalgamestore.entity.ProductEntity
import com.pwn233.digitalgamestore.exception.DigitalGameStoreException
import com.pwn233.digitalgamestore.repository.ProductRepository
import com.pwn233.digitalgamestore.repository.PromoCodeProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class DefaultGetProductFacade(
    private val productRepository: ProductRepository,
    private val promoCodeProductRepository: PromoCodeProductRepository
) {
    fun process(request: ProductRequest): Page<ProductResponse> {
        return try {
            validateConvertDataIsEmpty(convertDataToProductResponse(searchProduct(request)))
        } catch (e: Exception) {
            println("Exception Error : $e")
            throw e
        }
    }

    private fun searchProduct(request: ProductRequest): Page<ProductEntity>? {
        val pageable = PageRequest.of(request.pageNo - 1, request.pageSize)
        return productRepository.findProductWithCriteria(request.storeBranchId, request.name, request.price, request.description,
            request.category, request.brand, request.barcode, pageable)
    }

    private fun convertDataToProductResponse(productEntity: Page<ProductEntity>?): Page<ProductResponse> {
        return when (productEntity == null) {
            true -> Page.empty()
            false -> {
                val mapProduct : HashMap<String, Int> = hashMapOf()
                promoCodeProductRepository.countPromoCodeForProduct(productEntity.map{it.id}.toList())
                    .forEach{
                        println(it[0]+ " "+ it[1])
                        mapProduct[it[0]] = it[1].toInt()
                    }
                PageImpl(productEntity.map { ProductResponse(it.name, it.price, it.description, it.category,
                    it.brand, it.barcode, if(mapProduct.containsKey(it.id)) mapProduct[it.id] else 0, it.deleted) }
                    .toList(), productEntity.pageable, productEntity.totalElements)
            }
        }
    }

    private fun validateConvertDataIsEmpty(convertData: Page<ProductResponse>): Page<ProductResponse> {
        if(convertData.isEmpty) throw DigitalGameStoreException(HttpConstants.NO_RESPONSE_BODY)
        else return convertData
    }
}