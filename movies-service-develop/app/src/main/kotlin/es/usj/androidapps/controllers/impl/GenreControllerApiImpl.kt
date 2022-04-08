package es.usj.androidapps.controllers.impl

import es.usj.androidapps.controllers.GenreControllerApi
import es.usj.androidapps.model.dto.GenreDTO
import es.usj.androidapps.services.GenreServiceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class GenreControllerApiImpl : GenreControllerApi {
    @Autowired
    lateinit var genreService: GenreServiceApi

    override fun createGenre(body: GenreDTO): ResponseEntity<GenreDTO> {
        return try {
            ResponseEntity.ok().body(genreService.save(body))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun updateGenre(body: GenreDTO): ResponseEntity<Int> {
        return try {
            genreService.save(body)
            ResponseEntity.ok().body(1)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun deleteGenre(id: Long): ResponseEntity<GenreDTO> {
        return try {
            ResponseEntity.ok().body(genreService.delete(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getGenreById(id: Long): ResponseEntity<GenreDTO> {
        return try {
            ResponseEntity.ok().body(genreService.find(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getGenres(limit: Int?, offset: Long?): ResponseEntity<List<GenreDTO>> {
        return try {
            ResponseEntity.ok().body(genreService.list(limit, offset))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}