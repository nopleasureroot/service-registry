package com.monitoring.microservices.service.registry.service

import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance

interface InstanceService {
    fun addTarget(launchedInstanceDTO: LaunchedInstanceDTO): LaunchedInstanceDTO?
    fun checkInstanceHealth(port: Int, contextPath: String, instance: Instance): Boolean
}
