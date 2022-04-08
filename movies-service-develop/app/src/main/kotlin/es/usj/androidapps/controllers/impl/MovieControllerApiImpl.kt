package es.usj.androidapps.controllers.impl

import es.usj.androidapps.controllers.MovieControllerApi
import es.usj.androidapps.model.dto.MovieDTO
import es.usj.androidapps.services.MovieServiceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class MovieControllerApiImpl : MovieControllerApi {
    @Autowired
    lateinit var movieService: MovieServiceApi

    override fun createMovie(body: MovieDTO): ResponseEntity<MovieDTO> {
        return try {
            ResponseEntity.ok().body(movieService.save(body))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun updateMovie(body: MovieDTO): ResponseEntity<Int> {
        return try {
            movieService.save(body)
            ResponseEntity.ok().body(1)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun deleteMovie(id: Long): ResponseEntity<MovieDTO> {
        return try {
            ResponseEntity.ok().body(movieService.delete(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getMovieById(id: Long): ResponseEntity<MovieDTO> {
        return try {
            ResponseEntity.ok().body(movieService.find(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getMovies(limit: Int?, offset: Long?): ResponseEntity<List<MovieDTO>> {
        return try {
            ResponseEntity.ok().body(movieService.list(limit, offset))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}