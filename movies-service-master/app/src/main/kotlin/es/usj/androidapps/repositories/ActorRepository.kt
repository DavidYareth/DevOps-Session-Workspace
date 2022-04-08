package es.usj.androidapps.repositories

import es.usj.androidapps.model.Actor
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.stereotype.Repository

@Repository
interface ActorRepository : JpaRepositoryImplementation<Actor, Long> {
    fun findFirstByOrderByIdDesc() : Actor
}