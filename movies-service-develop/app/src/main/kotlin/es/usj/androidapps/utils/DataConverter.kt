package es.usj.androidapps.utils

import es.usj.androidapps.model.Actor
import es.usj.androidapps.model.Genre
import es.usj.androidapps.model.Movie
import es.usj.androidapps.model.dto.ActorDTO
import es.usj.androidapps.model.dto.GenreDTO
import es.usj.androidapps.model.dto.MovieDTO

object DataConverter {

    fun actorToDTO(actor: Actor): ActorDTO {
        return ActorDTO(
            actor.id,
            actor.name
        )
    }

    fun actorFromDTO(actorDTO: ActorDTO): Actor {
        return Actor(
            actorDTO.id,
            actorDTO.name,
            mutableListOf()
        )
    }

    fun genreToDTO(genre: Genre): GenreDTO {
        return GenreDTO(
            genre.id,
            genre.name
        )
    }

    fun genreFromDTO(genreDTO: GenreDTO): Genre {
        return Genre(
            genreDTO.id,
            genreDTO.name,
            mutableListOf()
        )
    }

    fun movieToDTO(movie: Movie): MovieDTO {
        return MovieDTO(
            movie.id,
            movie.title,
            movie.description,
            movie.director,
            movie.runtime,
            movie.year,
            movie.rating,
            movie.votes,
            movie.revenue,
            movie.actors.map { it.id },
            movie.genres.map { it.id }
        )
    }

    fun movieFromDTO(movieDTO: MovieDTO): Movie {
        return Movie(
            movieDTO.id,
            movieDTO.title,
            movieDTO.description,
            movieDTO.director,
            movieDTO.year,
            movieDTO.runtime,
            movieDTO.rating,
            movieDTO.votes,
            movieDTO.revenue,
            mutableListOf(),
            mutableListOf()
        )
    }
}