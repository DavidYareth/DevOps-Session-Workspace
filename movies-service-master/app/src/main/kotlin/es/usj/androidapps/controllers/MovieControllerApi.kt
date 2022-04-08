package es.usj.androidapps.controllers

import es.usj.androidapps.model.dto.MovieDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(value = "Existing Building Book Service", description = "Existing Building Book API")
@RequestMapping("movies")
interface MovieControllerApi {

    @ApiOperation(
        value = "Create a new movie.",
        nickname = "createMovie",
        notes = "Create a new movie.",
        response = MovieDTO::class,
        tags = ["Movies"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = MovieDTO::class),
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
    fun createMovie(@RequestBody @Valid body: MovieDTO): ResponseEntity<MovieDTO>

    @ApiOperation(
        value = "Updates a new movie.",
        nickname = "updateMovie",
        notes = "Updates a new movie.",
        response = Int::class,
        tags = ["Movies"]
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
    fun updateMovie(@RequestBody @Valid body: MovieDTO): ResponseEntity<Int>

    @ApiOperation(
        value = "Deletes a new movie.",
        nickname = "deleteMovie",
        notes = "Deletes a new movie.",
        response = MovieDTO::class,
        tags = ["Movies"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = MovieDTO::class),
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
    fun deleteMovie(@PathVariable id: Long): ResponseEntity<MovieDTO>

    @ApiOperation(
        value = "Get movie by id.",
        nickname = "getMovieById",
        notes = "Get movie by id.",
        response = MovieDTO::class,
        tags = ["Movies"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = MovieDTO::class),
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
    fun getMovieById(@PathVariable id: Long): ResponseEntity<MovieDTO>


    @ApiOperation(
        value = "Get movies.",
        nickname = "getMovies",
        notes = "Create a new movie.",
        response = MovieDTO::class,
        responseContainer = "List",
        tags = ["Movies"]
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK.", response = MovieDTO::class, responseContainer = "List"),
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
    fun getMovies(
        @RequestParam("limit", required = false) limit: Int? = 1500,
        @RequestParam("offset", required = false) offset: Long? = 0
    ): ResponseEntity<List<MovieDTO>>
}