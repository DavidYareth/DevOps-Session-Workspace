package es.usj.androidapps.controllers.impl

import es.usj.androidapps.controllers.ActorControllerApi
import es.usj.androidapps.model.dto.ActorDTO
import es.usj.androidapps.services.ActorServiceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class ActorControllerApiImpl : ActorControllerApi {

    @Autowired
    lateinit var actorServiceApi: ActorServiceApi

    override fun createActor(actorDTO: ActorDTO): ResponseEntity<ActorDTO> {
        return try {
            ResponseEntity.ok().body(actorServiceApi.save(actorDTO))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun updateActor(actorDTO: ActorDTO): ResponseEntity<Int> {
        return try {
            ResponseEntity.ok().body(actorServiceApi.edit(actorDTO))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun deleteActor(id: Long): ResponseEntity<ActorDTO> {
        return try {
            ResponseEntity.ok().body(actorServiceApi.delete(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getActorById(id: Long): ResponseEntity<ActorDTO> {
        return try {
            ResponseEntity.ok().body(actorServiceApi.find(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getActors(limit: Int?, offset: Long?): ResponseEntity<List<ActorDTO>> {
        return try {
            ResponseEntity.ok().body(actorServiceApi.list(limit, offset))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}