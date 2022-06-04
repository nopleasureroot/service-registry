package com.monitoring.microservices.service.registry.model.error

import java.time.LocalDate

sealed class ErrorResponse(
    open val statusCode: Int,
    override val message: String,
    open val errorCode: ErrorCode
): RuntimeException(errorCode.name) {
    val timestamp: LocalDate = LocalDate.now()
}
