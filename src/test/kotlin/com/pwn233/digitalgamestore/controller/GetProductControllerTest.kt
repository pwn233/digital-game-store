package com.pwn233.digitalgamestore.controller

import com.nhaarman.mockito_kotlin.any
import com.pwn233.digitalgamestore.common.Constants.LOG_ID
import com.pwn233.digitalgamestore.common.Constants.URI_INTERNAL
import com.pwn233.digitalgamestore.common.Constants.URI_STORE_MANAGEMENT
import com.pwn233.digitalgamestore.config.WebTestConfig
import com.pwn233.digitalgamestore.exception.DigitalGameStoreForHttpStatusException
import com.pwn233.digitalgamestore.facade.DefaultGetProductFacade
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.lang.Integer.valueOf

@WebMvcTest(controllers = [GetProductController::class])
@Import(WebTestConfig::class)
internal class GetProductControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var facade: DefaultGetProductFacade

    private val logId = "logId"

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

    private fun buildMockMvc(request: ProductRequest): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.get("$URI_INTERNAL$URI_STORE_MANAGEMENT/${request.storeBranchId}/product")
                .header(LOG_ID, logId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page_no", request.pageNo.toString())
                .param("page_size", request.pageSize.toString())
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Given Not Found Product - When called GetProductController - Should return no content`() {
        Mockito.`when`(facade.process(any())).thenThrow(DigitalGameStoreForHttpStatusException(HttpStatus.NO_CONTENT))
        buildMockMvc(mockProductRequest()).andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `Given Found Product without criteria - When called GetProductController - Should return success with data`() {
        val productResponse: List<ProductResponse> = listOf(
            ProductResponse("God Of War : Ragnarok", 1850.0,
                "An action-adventure game developed by Santa Monica Studio and published by Sony " +
                        "Interactive Entertainment. It was released worldwide on November 9, 2022,  " +
                        "marking the first cross-gen release in the God of War series.", "GAME-CODE",
                "Sony", "M94FfRzviLXE9VFy7dGL", 3, false),
            ProductResponse("Cyberpunk 2077", 1600.0, "An open-world, action-adventure " +
                    "RPG set in the megalopolis of Night City, where you play as a cyberpunk mercenary wrapped-up " +
                    "in a do-or-die fight for survival. Explore the dark future, now upgraded with next-gen in mind!",
                "GAME-CODE", "CD Projekt Red", "hJr6YUunPMxaxWd1zeII", 2, false)
        )
        val pageRequest = PageRequest.of(valueOf(mockProductRequest().pageNo), valueOf(mockProductRequest().pageSize))
        val pageResponse: Page<ProductResponse> = PageImpl(productResponse, pageRequest, productResponse.size.toLong())
        Mockito.`when`(facade.process(any())).thenReturn(pageResponse)
        buildMockMvc(mockProductRequest())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.status.code").value("0"))
            .andExpect(jsonPath("$.status.description").value("success"))
            .andExpect(jsonPath("$.status.title").doesNotHaveJsonPath())
            .andExpect(jsonPath("$.status.message").doesNotHaveJsonPath())
            .andExpect(jsonPath("$.data.content[0].name").value(productResponse[0].name))
            .andExpect(jsonPath("$.data.content[0].price", `is`(productResponse[0].price)))
            .andExpect(jsonPath("$.data.content[0].description", `is`(productResponse[0].description)))
            .andExpect(jsonPath("$.data.content[0].category", `is`(productResponse[0].category)))
            .andExpect(jsonPath("$.data.content[0].brand", `is`(productResponse[0].brand)))
            .andExpect(jsonPath("$.data.content[0].barcode", `is`(productResponse[0].barcode)))
            .andExpect(jsonPath("$.data.content[0].number_of_promo_code", `is`(productResponse[0].numberOfPromoCode)))
            .andExpect(jsonPath("$.data.content[0].deleted", `is` (productResponse[0].deleted)))
            .andExpect(jsonPath("$.data.content[1].name").value(productResponse[1].name))
            .andExpect(jsonPath("$.data.content[1].price", `is`(productResponse[1].price)))
            .andExpect(jsonPath("$.data.content[1].description", `is`(productResponse[1].description)))
            .andExpect(jsonPath("$.data.content[1].category", `is`(productResponse[1].category)))
            .andExpect(jsonPath("$.data.content[1].brand", `is`(productResponse[1].brand)))
            .andExpect(jsonPath("$.data.content[1].barcode", `is`(productResponse[1].barcode)))
            .andExpect(jsonPath("$.data.content[1].number_of_promo_code", `is`(productResponse[1].numberOfPromoCode)))
            .andExpect(jsonPath("$.data.content[1].deleted", `is` (productResponse[1].deleted)))
    }
    @Test
    fun `Given Found Product with search criteria - When called GetProductController - Should return success with data`() {
        mockProductRequest(name = "Cyberpunk 2077")
        val productResponse: List<ProductResponse> = listOf(
            ProductResponse("Cyberpunk 2077", 1600.0, "An open-world, action-adventure " +
                    "RPG set in the megalopolis of Night City, where you play as a cyberpunk mercenary wrapped-up " +
                    "in a do-or-die fight for survival. Explore the dark future, now upgraded with next-gen in mind!",
                "GAME-CODE", "CD Projekt Red", "hJr6YUunPMxaxWd1zeII", 2, false))
        val pageRequest = PageRequest.of(valueOf(mockProductRequest().pageNo), valueOf(mockProductRequest().pageSize))
        val pageResponse: Page<ProductResponse> = PageImpl(productResponse, pageRequest, productResponse.size.toLong())
        Mockito.`when`(facade.process(any())).thenReturn(pageResponse)
        buildMockMvc(mockProductRequest())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.status.code").value("0"))
            .andExpect(jsonPath("$.status.description").value("success"))
            .andExpect(jsonPath("$.status.title").doesNotHaveJsonPath())
            .andExpect(jsonPath("$.status.message").doesNotHaveJsonPath())
            .andExpect(jsonPath("$.data.content[0].name").value(productResponse[0].name))
            .andExpect(jsonPath("$.data.content[0].price", `is`(productResponse[0].price)))
            .andExpect(jsonPath("$.data.content[0].description", `is`(productResponse[0].description)))
            .andExpect(jsonPath("$.data.content[0].category", `is`(productResponse[0].category)))
            .andExpect(jsonPath("$.data.content[0].brand", `is`(productResponse[0].brand)))
            .andExpect(jsonPath("$.data.content[0].barcode", `is`(productResponse[0].barcode)))
            .andExpect(jsonPath("$.data.content[0].number_of_promo_code", `is`(productResponse[0].numberOfPromoCode)))
    }

}