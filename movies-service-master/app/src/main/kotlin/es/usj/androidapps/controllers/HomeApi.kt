package es.usj.androidapps.controllers

import es.usj.androidapps.model.dto.HealthResponseDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Api(value = "Home", description = "Home Api")
interface HomeApi {
    @ApiOperation(
        value = "Get a microservice status.",
        nickname = "healthCheck",
        notes = "Status must be equals to OK or error",
        response = HealthResponseDTO::class,
        tags = ["Home"]
    )
    @ApiResponses(
        value = [ApiResponse(code = 200, message = "OK.", response = HealthResponseDTO::class),
            ApiResponse(code = 500, message = "Server error.", response = Error::class)]
    )
    @RequestMapping(
        value = ["/health-check"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun healthCheck(): ResponseEntity<HealthResponseDTO>

    @RequestMapping(
        value = ["/"],
        method = [RequestMethod.GET]
    )
    fun index(): String
}