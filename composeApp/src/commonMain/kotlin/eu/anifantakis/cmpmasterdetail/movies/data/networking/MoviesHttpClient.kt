package eu.anifantakis.cmpmasterdetail.movies.data.networking

import eu.anifantakis.cmpmasterdetail.core.data.networking.CommonHttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom

class MoviesHttpClient(
    baseUrl: String,
    engine: HttpClientEngine = CIO.create(),
    private val apiKey: String? = null,
    logging: Boolean = true
) : CommonHttpClient(
    baseUrl = baseUrl,
    logging = logging,
    engine = engine
){

    override val additionalConfig: (HttpClientConfig<*>.() -> Unit) = {
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                takeFrom(baseUrl)
                if (apiKey != null) {
                    parameters.append("api_key", apiKey)
                }
            }
        }

        // Or if you prefer headers
        defaultRequest {
            contentType(ContentType.Application.Json)
            if (apiKey != null) {
                header("x-api-key", apiKey)
            }
            url {
                takeFrom(baseUrl)
            }
        }
    }
}