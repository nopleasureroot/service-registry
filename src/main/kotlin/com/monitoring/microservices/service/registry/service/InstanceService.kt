package com.monitoring.microservices.service.registry.service

import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO

interface InstanceService {
    fun addTarget(launchedInstanceDTO: LaunchedInstanceDTO): LaunchedInstanceDTO?
}
