package com.monitoring.microservices.service.registry.model.error

class LaunchInstanceErrorResponse(
    override val statusCode: Int,
    override val message: String
    )
    : ErrorResponse(statusCode, message, ErrorCode.ERR_LAUNCH_INSTANCE_EXCEPTION)
