package com.monitoring.microservices.service.registry.service

import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.model.response.RegisteredInstanceResponse
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegistryService(val instancesRepository: InstancesRepository) {
    fun registerInstance(): RegisteredInstanceResponse {
        val instanceUUID: UUID = UUID.randomUUID()
        instancesRepository.save(Instance(instanceUUID))

        return RegisteredInstanceResponse(instanceUUID.toString())
    }
}
