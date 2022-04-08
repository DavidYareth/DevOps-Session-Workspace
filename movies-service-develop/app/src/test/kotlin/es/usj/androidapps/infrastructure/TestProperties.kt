package es.usj.androidapps.infrastructure

import org.glassfish.jersey.client.JerseyClientBuilder
import java.util.concurrent.TimeUnit
import javax.ws.rs.client.Client

internal const val LOCAL = "http://localhost:8080"

data class TestProperties(
    val client: Client = JerseyClientBuilder()
        .connectTimeout(30000, TimeUnit.MILLISECONDS)
        .readTimeout(30000, TimeUnit.MILLISECONDS)
        .build(),
    var baseUrl: String,
    var token: String,
    val environment: Environment = Environment.LOCAL
) {
    companion object {

        fun local() = TestProperties(
            environment = Environment.LOCAL,
            baseUrl = LOCAL,
            token = ""
        )

        fun mock() = TestProperties(
            environment = Environment.LOCAL,
            baseUrl = LOCAL,
            token = ""
        )

        fun qa() = TestProperties(environment = Environment.QA, baseUrl = LOCAL, token = "")
        fun develop() = TestProperties(environment = Environment.TEST, baseUrl = LOCAL, token = "")
        fun production() = TestProperties(environment = Environment.PRODUCTION, baseUrl = LOCAL, token = "")
    }
}
