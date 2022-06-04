package com.monitoring.microservices.service.registry.service

import com.monitoring.microservices.service.registry.model.dto.RegisteredInstanceDTO
import com.monitoring.microservices.service.registry.model.request.RegistryBody

interface RegistryService {
    fun registerInstance(registryBody: RegistryBody): RegisteredInstanceDTO
}
