package com.pwn233.digitalgamestore.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product")
class ProductEntity (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val storeBranchId: String,
    val name: String,
    val price: Double,
    val description: String? = null,
    val category: String? = null,
    val brand: String? = null,
    val barcode: String? = null,
    val deleted: Boolean = false,
    val createdTime: LocalDateTime = LocalDateTime.now(),
    val createdBy: String,
    val updatedTime: LocalDateTime? = null,
    val updatedBy: String? = null
)