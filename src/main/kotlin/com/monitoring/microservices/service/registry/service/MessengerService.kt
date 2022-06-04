package com.monitoring.microservices.service.registry.service

import java.net.http.HttpResponse

interface MessengerService<T> {
    fun executePostRequest(body: T, url: String): HttpResponse<String>?
    fun executeGetRequest(url: String)
}
