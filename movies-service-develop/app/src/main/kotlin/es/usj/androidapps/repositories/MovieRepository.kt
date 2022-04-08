package es.usj.androidapps.repositories

import es.usj.androidapps.model.Movie
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepositoryImplementation<Movie, Long> {
    fun findFirstByOrderByIdDesc() : Movie
}