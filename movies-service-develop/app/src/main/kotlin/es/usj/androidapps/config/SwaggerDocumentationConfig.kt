package com.movicoders.saas.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.sql.Date
import javax.annotation.Generated


@Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2020-08-25T11:13:13.435Z[GMT]")
@Configuration
@EnableOpenApi
class SwaggerDocumentationConfig {

    @Value("\${microservice.title}")
    private val title: String? = null

    @Value("\${microservice.description}")
    private val description: String? = null

    @Value("\${microservice.base.package}")
    private val basePackage: String? = null

    fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("$title")
            .description("$description")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .contact(Contact("Movicoders", "http://movicoders.com", ""))
            .build()
    }

    @Bean
    fun customImplementation(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .build()
            .directModelSubstitute(LocalDate::class.java, Date::class.java)
            .directModelSubstitute(OffsetDateTime::class.java, java.util.Date::class.java)
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    private fun authorizationScopes(): Array<AuthorizationScope> {
        return arrayOf(
            AuthorizationScope("global", "write accessEverything")
        )
    }

    fun defaultAuth(): List<SecurityReference> {
        return listOf(SecurityReference("Authorization", authorizationScopes()))
    }
}