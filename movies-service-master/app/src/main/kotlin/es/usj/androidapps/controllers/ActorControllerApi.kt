package es.usj.androidapps.controllers

import es.usj.androidapps.model.dto.ActorDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "Existing Building Book Service", description = "Existing Building Book API")
@RequestMapping("actors")
interface ActorControllerApi {

    @ApiOperation(
        value = "Create a new actor.",
        nickname = "createActor",
        notes = "Create a new actor.",
        response = ActorDTO::class,
        tags = ["Actors"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = ActorDTO::class),
            ApiResponse(code = 400, message = "Invalid Credentials.", response = Error::class),
            ApiResponse(code = 401, message = "Unauthorized.", response = Error::class),
            ApiResponse(code = 403, message = "Forbidden.", response = Error::class),
            ApiResponse(code = 404, message = "Not found.", response = Error::class),
            ApiResponse(code = 500, message = "Server error.", response = Error::class)
        ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createActor(@RequestBody @Valid actorDTO: ActorDTO): ResponseEntity<ActorDTO>

    @ApiOperation(
        value = "Updates a new actor.",
        nickname = "updateActor",
        notes = "Updates a new actor.",
        response = Int::class,
        tags = ["Actors"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = Int::class),
            ApiResponse(code = 400, message = "Invalid Credentials.", response = Error::class),
            ApiResponse(code = 401, message = "Unauthorized.", response = Error::class),
            ApiResponse(code = 403, message = "Forbidden.", response = Error::class),
            ApiResponse(code = 404, message = "Not found.", response = Error::class),
            ApiResponse(code = 500, message = "Server error.", response = Error::class)
        ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun updateActor(@RequestBody @Valid actorDTO: ActorDTO): ResponseEntity<Int>

    @ApiOperation(
        value = "Deletes a new actor.",
        nickname = "deleteActor",
        notes = "Deletes a new actor.",
        response = ActorDTO::class,
        tags = ["Actors"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = ActorDTO::class),
            ApiResponse(code = 400, message = "Invalid Credentials.", response = Error::class),
            ApiResponse(code = 401, message = "Unauthorized.", response = Error::class),
            ApiResponse(code = 403, message = "Forbidden.", response = Error::class),
            ApiResponse(code = 404, message = "Not found.", response = Error::class),
            ApiResponse(code = 500, message = "Server error.", response = Error::class)
        ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteActor(@PathVariable id: Long): ResponseEntity<ActorDTO>

    @ApiOperation(
        value = "Get actor by id.",
        nickname = "getActorById",
        notes = "Get actor by id.",
        response = ActorDTO::class,
        tags = ["Actors"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = ActorDTO::class),
            ApiResponse(code = 400, message = "Invalid Credentials.", response = Error::class),
            ApiResponse(code = 401, message = "Unauthorized.", response = Error::class),
            ApiResponse(code = 403, message = "Forbidden.", response = Error::class),
            ApiResponse(code = 404, message = "Not found.", response = Error::class),
            ApiResponse(code = 500, message = "Server error.", response = Error::class)
        ]
    )
    @RequestMapping(
        path = ["/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getActorById(@PathVariable id: Long): ResponseEntity<ActorDTO>


    @ApiOperation(
        value = "Get actors.",
        nickname = "getActors",
        notes = "Create a new actor.",
        response = ActorDTO::class,
        responseContainer = "List",
        tags = ["Actors"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = ActorDTO::class, responseContainer = "List"),
            ApiResponse(code = 400, message = "Invalid Credentials.", response = Error::class),
            ApiResponse(code = 401, message = "Unauthorized.", response = Error::class),
            ApiResponse(code = 403, message = "Forbidden.", response = Error::class),
            ApiResponse(code = 404, message = "Not found.", response = Error::class),
            ApiResponse(code = 500, message = "Server error.", response = Error::class)
        ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getActors(
        @RequestParam("limit", required = false) limit: Int? = 1000,
        @RequestParam("offset", required = false) offset: Long? = 0
    ): ResponseEntity<List<ActorDTO>>
}