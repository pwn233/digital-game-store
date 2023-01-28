package com.pwn233.digitalgamestore.facade

import com.pwn233.digitalgamestore.common.HttpConstants
import com.pwn233.digitalgamestore.controller.ProductRequest
import com.pwn233.digitalgamestore.entity.ProductEntity
import com.pwn233.digitalgamestore.exception.DigitalGameStoreException
import com.pwn233.digitalgamestore.repository.ProductRepository
import com.pwn233.digitalgamestore.repository.PromoCodeProductRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
internal class DefaultGetProductByStoreBranchIdFacadeTest {

    lateinit var subject: DefaultGetProductByStoreBranchIdFacade

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var promoCodeProductRepository: PromoCodeProductRepository

    @BeforeEach
    fun setUp() { subject = DefaultGetProductByStoreBranchIdFacade(productRepository, promoCodeProductRepository) }

    private fun mockProductRequest(
        storeBranchId: String = "DGS-BKK-001",
        name: String? = null,
        price: Double? = null,
        description: String? = null,
        category: String? = null,
        brand: String? = null,
        barcode: String? = null,
        pageNo: Int = 1,
        pageSize: Int = 10
    ): ProductRequest { return ProductRequest(storeBranchId, name, price,
        description, category, brand, barcode, pageNo, pageSize) }

    @Test
    fun `Given Not Found Product without search criteria - GetProductFacade - Should return no content`() {
        every { productRepository.findProductWithCriteria(mockProductRequest().storeBranchId,
            any(), any(), any(), any(), any(), any(), any()) } answers { null }
        val actualException = Assertions.assertThrows(DigitalGameStoreException::class.java) {
            subject.process(mockProductRequest()) }
        Assertions.assertEquals(HttpConstants.NO_RESPONSE_BODY, actualException.status)
    }

    @Test
    fun `Given Not Found Product with search criteria - GetProductFacade - Should return no content`() {
        every { productRepository.findProductWithCriteria(mockProductRequest().storeBranchId,
            any(), any(), any(), any(), any(), any(), any()) } answers { null }
        val actualException = Assertions.assertThrows(DigitalGameStoreException::class.java) {
            subject.process(mockProductRequest(name = "God of Door : Heaven")) }
        Assertions.assertEquals(HttpConstants.NO_RESPONSE_BODY, actualException.status)
    }

    @Test
    fun `Given found Sku Number with no number of promo code- When called GetSkuNumberByOrganizationIdFacade - Then return data`() {
        val mockProductRequest = mockProductRequest()
        val productResponse: List<ProductEntity> = arrayListOf( ProductEntity("P-0001", "DGS-BKK-001",
            "God Of War : Ragnarok", 1850.0, "An action-adventure game developed by Santa Monica" +
                    " Studio and published by Sony Interactive Entertainment. It was released worldwide on November " +
                    "9, 2022, marking the first cross-gen release in the God of War series.", "GAME-CODE",
            "Sony", "M94FfRzviLXE9VFy7dGL", false, LocalDateTime.parse("2022-01-12T02:33:01"),
            "admin"))
        val pageRequest = PageRequest.of( Integer.valueOf(mockProductRequest().pageNo),  Integer.valueOf(mockProductRequest().pageSize) )
        val pageResponse: Page<ProductEntity> = PageImpl(productResponse, pageRequest, 0)
        every { productRepository.findProductWithCriteria(mockProductRequest.storeBranchId, any(), any(), any(), any(), any(), any(), any())
        } answers { pageResponse }
        val promoCodeCountResponse: List<Array<String>> = listOf(arrayOf("P-0001", "3"), arrayOf("P-0002", "2"),
            arrayOf("P-0003", "0"), arrayOf("P-0004", "0"), arrayOf("P-0005", "0"))
        every { promoCodeProductRepository.countPromoCodeForProduct(arrayListOf("P-0001"))
        } answers { promoCodeCountResponse }
        val actualResponse = subject.process(mockProductRequest(name = "God Of War"))

        println(actualResponse.content[0])

        assertEquals(1, actualResponse.content.size)
        assertEquals(productResponse[0].name, actualResponse.content[0].name)
        assertEquals(productResponse[0].price, actualResponse.content[0].price)
        assertEquals(productResponse[0].description, actualResponse.content[0].description)
        assertEquals(productResponse[0].category, actualResponse.content[0].category)
        assertEquals(productResponse[0].brand ,actualResponse.content[0].brand)
        assertEquals(productResponse[0].barcode, actualResponse.content[0].barcode)
        assertEquals(3, actualResponse.content[0].numberOfPromoCode)
        assertEquals(false, actualResponse.content[0].deleted)
    }

}