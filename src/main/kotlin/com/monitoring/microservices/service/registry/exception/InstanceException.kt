package com.monitoring.microservices.service.registry.exception

open class InstanceException(
    errorCode: ErrorCode,
    ) : RuntimeException(errorCode.name)
