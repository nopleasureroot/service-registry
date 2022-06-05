package com.monitoring.microservices.service.registry.exception

open class RegistryException(
    errorCode: ErrorCode,
) : RuntimeException(errorCode.name)
