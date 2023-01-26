package com.pwn233.digitalgamestore.repository

import com.pwn233.digitalgamestore.entity.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, String> {

    @Query("SELECT p FROM ProductEntity p WHERE p.storeBranchId = :storeBranchId AND" +
            "(:name is null OR p.name LIKE CONCAT ('%',:name,'%')) AND " +
            "(:price is null OR p.price LIKE CONCAT('%', :price, '%')) AND " +
            "(:description is null OR p.description LIKE CONCAT('%',:description,'%')) AND " +
            "(:category is null OR p.category LIKE CONCAT('%',:category,'%')) AND " +
            "(:brand is null OR p.brand LIKE CONCAT('%',:brand,'%')) AND " +
            "(:barcode is null OR p.barcode LIKE CONCAT ('%',:barcode, '%')) ORDER BY p.id ASC ")
    fun findProductWithCriteria(storeBranchId: String, name: String?, price: Double?, description: String?, category: String?,
                                  brand: String?, barcode: String?, pageable: Pageable
    ): Page<ProductEntity>?
}