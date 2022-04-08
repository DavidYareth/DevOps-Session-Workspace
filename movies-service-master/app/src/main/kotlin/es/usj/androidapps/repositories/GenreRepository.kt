package es.usj.androidapps.repositories

import es.usj.androidapps.model.Genre
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository

@Repository
interface GenreRepository : JpaRepositoryImplementation<Genre, Long> {
    fun findFirstByOrderByIdDesc() : Genre
}