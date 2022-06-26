package com.monitoring.microservices.service.registry.util

import java.net.URI

class ValidationUtils {
    companion object {
        fun validateURL(url: String): Boolean {
            return try {
                URI.create(url)

                true
            } catch (exc: Exception) {
                false
            }
        }
    }
}
