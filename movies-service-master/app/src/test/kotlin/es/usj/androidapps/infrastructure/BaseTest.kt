package es.usj.androidapps.infrastructure

import org.eclipse.jetty.http.HttpMethod
import org.glassfish.jersey.client.ClientProperties
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.ws.rs.WebApplicationException
import javax.ws.rs.client.Entity

abstract class BaseTest(val p: TestProperties) {

    protected inline fun <reified RETURN> String.POST(
        entity: Any,
        headers: Map<String, String>? = null,
        vararg queryParams: Pair<String, String>
    ): RETURN =
        this.request(method = HttpMethod.POST, body = entity, headers = headers, queryParams = queryParams)

    protected inline fun <reified RETURN> String.PUT(
        entity: Any,
        headers: Map<String, String>? = null,
        vararg queryParams: Pair<String, String>
    ): RETURN =
        this.request(method = HttpMethod.PUT, headers = headers, body = entity, queryParams = queryParams)

    protected inline fun <reified RETURN> String.GET(
        headers: Map<String, String>? = null,
        vararg queryParams: Pair<String, String>
    ): RETURN =
        this.request(method = HttpMethod.GET, headers = headers, queryParams = queryParams)

    protected inline fun <reified RETURN> String.DELETE(
        headers: Map<String, String>? = null,
        vararg queryParams: Pair<String, String>
    ): RETURN =
        this.request(method = HttpMethod.DELETE, headers = headers, queryParams = queryParams)

    protected inline fun <reified RETURN> String.request(
        method: HttpMethod,
        headers: Map<String, String>? = null,
        body: Any? = null,
        vararg queryParams: Pair<String, String>
    ): RETURN {
        p.client.property(ClientProperties.CONNECT_TIMEOUT, 30000)
        p.client.property(ClientProperties.READ_TIMEOUT, 30000)
        var target =
            p.client.target(p.baseUrl.let { "${p.baseUrl.removeSuffix("/")}/${this.removePrefix("/")}" })

        queryParams.forEach { (k, v) ->
            target = target.queryParam(k, URLEncoder.encode(v, StandardCharsets.UTF_8.toString()))
        }
        return try {
            target.request().apply {
                if (p.token.isNotBlank()) header("Authorization", "Bearer ${p.token}")
                headers?.forEach { (key, value) ->
                    header(key, value)
                }
            }.method(method.toString(), body?.let { Entity.json(it) }, RETURN::class.java)
        } catch (e: Exception) {
            when (e) {
                is WebApplicationException -> println(e.response.readEntity(String::class.java))
            }
            throw e
        }
    }
}