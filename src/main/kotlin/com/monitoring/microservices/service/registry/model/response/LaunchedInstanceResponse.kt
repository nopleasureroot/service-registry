package com.monitoring.microservices.service.registry.model.response

import com.monitoring.microservices.service.registry.model.entity.Target

data class LaunchedInstanceResponse(val uuid: String, val targets: List<Target>)
