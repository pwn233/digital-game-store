package com.pwn233.digitalgamestore.repository

import com.pwn233.digitalgamestore.entity.PromoCodeProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PromoCodeProductRepository : JpaRepository<PromoCodeProductEntity, String> {

    @Query("SELECT product_id, sum(case when product_id in(:productId) then 1 else 0 end) AS promoCount " +
            "FROM promo_code_product pcp GROUP BY product_id ", nativeQuery = true)
    fun countPromoCodeForProduct(productId: List<String>): List<Array<String>>
}