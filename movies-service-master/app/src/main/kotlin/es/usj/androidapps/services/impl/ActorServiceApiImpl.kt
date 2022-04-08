package es.usj.androidapps.services.impl

import es.usj.androidapps.model.dto.ActorDTO
import es.usj.androidapps.repositories.ActorRepository
import es.usj.androidapps.services.ActorServiceApi
import es.usj.androidapps.utils.DataConverter
import es.usj.androidapps.utils.OffsetBasedPageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActorServiceApiImpl : ActorServiceApi {

    @Autowired
    lateinit var actorRepository: ActorRepository

    override fun list(limit: Int?, offset: Long?): List<ActorDTO> {
        if (limit != null && offset != null) {
            val pageable = OffsetBasedPageRequest(
                offset,
                limit
            )
            return actorRepository.findAll(pageable).toList().map { DataConverter.actorToDTO(it) }
        }
        return actorRepository.findAll().map { DataConverter.actorToDTO(it) }
    }

    override fun find(id: Long): ActorDTO? {
        return DataConverter.actorToDTO(actorRepository.findById(id).get())
    }

    override fun delete(id: Long): ActorDTO {
        val actor = find(id)
        if (actor != null) {
            actorRepository.deleteById(id)
            return actor
        } else {
            throw Exception("Actor not found")
        }
    }

    override fun save(element: ActorDTO): ActorDTO {
        val item = DataConverter.actorFromDTO(element)
        item.id = actorRepository.findFirstByOrderByIdDesc().id + 1
        return DataConverter.actorToDTO(actorRepository.save(item))
    }

    override fun edit(element: ActorDTO): Int {
        val item = DataConverter.actorFromDTO(element)
        val actor = actorRepository.findById(item.id).get()
        actor.name = item.name
        actorRepository.save(actor)
        return 1
    }
}