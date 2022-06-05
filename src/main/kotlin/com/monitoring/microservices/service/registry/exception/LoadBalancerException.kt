package com.monitoring.microservices.service.registry.exception

class LoadBalancerException(
    errorCode: ErrorCode,
) : RuntimeException(errorCode.name)
