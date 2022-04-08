package es.usj.androidapps.controllers

import es.usj.androidapps.model.dto.GenreDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "Existing Building Book Service", description = "Existing Building Book API")
@RequestMapping("genres")
interface GenreControllerApi {

    @ApiOperation(
        value = "Create a new genre.",
        nickname = "createGenre",
        notes = "Create a new genre.",
        response = GenreDTO::class,
        tags = ["Genres"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = GenreDTO::class),
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
    fun createGenre(@RequestBody @Valid body: GenreDTO): ResponseEntity<GenreDTO>

    @ApiOperation(
        value = "Updates a new genre.",
        nickname = "updateGenre",
        notes = "Updates a new genre.",
        response = Int::class,
        tags = ["Genres"]
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
    fun updateGenre(@RequestBody @Valid body: GenreDTO): ResponseEntity<Int>

    @ApiOperation(
        value = "Deletes a new genre.",
        nickname = "deleteGenre",
        notes = "Deletes a new genre.",
        response = GenreDTO::class,
        tags = ["Genres"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = GenreDTO::class),
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
    fun deleteGenre(@PathVariable id: Long): ResponseEntity<GenreDTO>

    @ApiOperation(
        value = "Get genre by id.",
        nickname = "getGenreById",
        notes = "Get genre by id.",
        response = GenreDTO::class,
        tags = ["Genres"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = GenreDTO::class),
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
    fun getGenreById(@PathVariable id: Long): ResponseEntity<GenreDTO>


    @ApiOperation(
        value = "Get genres.",
        nickname = "getGenres",
        notes = "Create a new genre.",
        response = GenreDTO::class,
        responseContainer = "List",
        tags = ["Genres"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = GenreDTO::class, responseContainer = "List"),
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
    fun getGenres(
        @RequestParam("limit", required = false) limit: Int? = 1000,
        @RequestParam("offset", required = false) offset: Long? = 0
    ): ResponseEntity<List<GenreDTO>>
}