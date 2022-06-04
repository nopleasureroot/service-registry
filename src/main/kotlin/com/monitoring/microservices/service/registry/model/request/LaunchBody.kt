package com.monitoring.microservices.service.registry.model.request

import com.monitoring.microservices.service.registry.model.dto.TargetDTO

data class LaunchBody(val targets: List<TargetDTO>, val contextPath: String, val port: Int)
