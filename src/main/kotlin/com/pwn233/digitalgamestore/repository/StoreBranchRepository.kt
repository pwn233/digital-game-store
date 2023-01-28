package com.pwn233.digitalgamestore.repository

import com.pwn233.digitalgamestore.entity.StoreBranchEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreBranchRepository : JpaRepository<StoreBranchEntity, String> {

}