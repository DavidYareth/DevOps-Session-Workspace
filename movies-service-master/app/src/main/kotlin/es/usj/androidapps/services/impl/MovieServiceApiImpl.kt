package es.usj.androidapps.services.impl

import es.usj.androidapps.model.dto.MovieDTO
import es.usj.androidapps.repositories.ActorRepository
import es.usj.androidapps.repositories.GenreRepository
import es.usj.androidapps.repositories.MovieRepository
import es.usj.androidapps.services.MovieServiceApi
import es.usj.androidapps.utils.DataConverter
import es.usj.androidapps.utils.OffsetBasedPageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MovieServiceApiImpl : MovieServiceApi {

    @Autowired
    lateinit var movieRepository: MovieRepository

    @Autowired
    lateinit var genreRepository: GenreRepository

    @Autowired
    lateinit var actorRepository: ActorRepository

    override fun list(limit: Int?, offset: Long?): List<MovieDTO> {
        if (limit != null && offset != null) {
            val pageable = OffsetBasedPageRequest(
                offset,
                limit
            )
            return movieRepository.findAll(pageable).toList().map { DataConverter.movieToDTO(it) }
        }
        return movieRepository.findAll().map { DataConverter.movieToDTO(it) }
    }

    override fun find(id: Long): MovieDTO? {
        return DataConverter.movieToDTO(movieRepository.findById(id).get())
    }

    override fun delete(id: Long): MovieDTO {
        val movie = find(id)
        if (movie != null) {
            movieRepository.deleteById(id)
            return movie
        } else {
            throw Exception("Movie not found")
        }
    }

    override fun save(element: MovieDTO): MovieDTO {
        val item = DataConverter.movieFromDTO(element)
        item.id = movieRepository.findFirstByOrderByIdDesc().id + 1
        return DataConverter.movieToDTO(movieRepository.save(item))
    }

    override fun edit(element: MovieDTO): Int {
        val item = DataConverter.movieFromDTO(element)
        val genres = element.genres.map { genreRepository.findById(it).get() }
        item.addAllGenres(genres)
        val actors = element.actors.map { actorRepository.findById(it).get() }
        item.addAllActors(actors)
        movieRepository.save(item)
        return 1
    }
}