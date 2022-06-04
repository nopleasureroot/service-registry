package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.model.dto.RegisteredInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.model.request.RegistryBody
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.service.RegistryService
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegistryServiceImpl(val instancesRepository: InstancesRepository): RegistryService {
    override fun registerInstance(registryBody: RegistryBody): RegisteredInstanceDTO {
        val instanceUUID: UUID = UUID.randomUUID()
        instancesRepository.save(Instance(
            id = instanceUUID,
            port = registryBody.port,
            path = registryBody.contextPath)
        )

        return RegisteredInstanceDTO(instanceUUID.toString())
    }
}
