package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.service.MessengerService
import com.monitoring.microservices.service.registry.util.Constants
import com.monitoring.microservices.service.registry.util.HttpFabric
import lombok.extern.slf4j.Slf4j
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.ConnectException
import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.net.http.HttpTimeoutException

@Service
class MessengerServiceImpl<T>: MessengerService<T> {
    private val httpClient: HttpClient = HttpFabric.httpClient

    override fun executePostRequest(body: T, url: String): HttpResponse<String>? {
        return try {
            httpClient.send(
                HttpFabric.createPostRequest(url, JSONObject(body).toString()),
                HttpResponse.BodyHandlers.ofString()
            )
        } catch (exc: ConnectException) {
            exc.printStackTrace()

            null
        } catch (exc: HttpTimeoutException) {
            exc.printStackTrace()

            null
        }
    }

    override fun executeGetRequest(url: String) {
    }
}
