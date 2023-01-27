package com.pwn233.digitalgamestore.config

import com.pwn233.digitalgamestore.common.Constants.BUILD_VERSION
import com.pwn233.digitalgamestore.common.HttpConstants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.DocExpansion
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*
import java.util.stream.Collectors


@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    companion object {
        const val STORE_MANAGEMENT = "Store Management"
        const val ACCOUNT_MANAGEMENT = "Account Management"
    }

    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .paths(PathSelectors.ant("/**/"))
            .build()
            .tags(
                TitleTag(STORE_MANAGEMENT),
                TitleTag(ACCOUNT_MANAGEMENT)
            ) .apiInfo(
                ApiInfo(
                    "Digital-Game-Store API",
                    getResponseDetail(),
                    BUILD_VERSION,
                    "",
                    null,
                    "",
                    "",
                    emptyList()
                )
            )
    }

    @Bean
    fun uiConfig(): UiConfiguration? {
        return UiConfigurationBuilder.builder()
            .defaultModelsExpandDepth(-1)
            .docExpansion(DocExpansion.LIST)
            .build()
    }
}
class TitleTag(name: String) : Tag(name, "")

fun getResponseDetail(): String {
    return "Possible response status code : "+System.lineSeparator()+ Arrays.stream(HttpConstants.values())
                .map {""+it+" -> [ "+it.code + ", " + it.message + " ]" }.collect(Collectors.joining(System.lineSeparator()))
}
