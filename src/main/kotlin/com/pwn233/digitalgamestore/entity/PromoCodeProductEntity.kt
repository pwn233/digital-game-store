package com.pwn233.digitalgamestore.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "promo_code_product")
class PromoCodeProductEntity(

    @EmbeddedId
    val id: PromoCodeProductId,
    val createdTime: LocalDateTime = LocalDateTime.now(),
    val createdBy: String

)

@Embeddable
class PromoCodeProductId(
    val promoCodeId: String,
    val productId: String
) : Serializable