package com.pwn233.digitalgamestore.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "store_branch")
class StoreBranchEntity (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val contactMail: String,
    val contactPhoneNumber: String,
    val address: String,
    val createdTime: LocalDateTime = LocalDateTime.now(),
    val createdBy: String,
    val updatedTime: LocalDateTime? = null,
    val updatedBy: String? = null
)