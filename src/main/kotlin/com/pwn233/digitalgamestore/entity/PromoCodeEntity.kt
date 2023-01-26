package com.pwn233.digitalgamestore.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "promo_code")
class PromoCodeEntity (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val storeBranchId: String,
    val promoCode: String,
    val startDate: Date,
    val endDate: Date,
    val deleted: Boolean = false,
    val createdTime: LocalDateTime = LocalDateTime.now(),
    val createdBy: String,
    val updatedTime: LocalDateTime? = null,
    val updatedBy: String? = null
)