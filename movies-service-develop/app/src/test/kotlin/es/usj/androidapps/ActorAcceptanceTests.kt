package es.usj.androidapps

import com.fasterxml.jackson.databind.ObjectMapper
import es.usj.androidapps.infrastructure.BaseTest
import es.usj.androidapps.infrastructure.TestProperties
import es.usj.androidapps.model.dto.ActorDTO
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

const val PATH = "/actors"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ActorAcceptanceTests : BaseTest(TestProperties.local()) {

    private inline fun <reified U> list(
        path: String,
        objectMapper: ObjectMapper?,
        headers: Map<String, String>? = null,
        vararg queryParams: Pair<String, String>
    ): ArrayList<U> {
        val result = path.GET<ArrayList<U>>(headers, *queryParams)
        if (objectMapper != null) {
            val response = objectMapper.typeFactory.constructCollectionType(
                ArrayList::class.java,
                U::class.java
            )
            return objectMapper.convertValue(result, response)
        }
        return result
    }

    @Test
    fun `add actor without id returns actor with maximum id`() {
        val actors = list<ActorDTO>(PATH, null, null)
        val size = actors.count()
        val actor = ActorDTO(0, "Juanjo")
        val returned = PATH.POST<ActorDTO>(actor)
        assert(returned.id > size)
        assert(list<ActorDTO>(PATH, null, null).count() > size)
    }
}