package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.exception.ErrorCode
import com.monitoring.microservices.service.registry.exception.RegistryException
import com.monitoring.microservices.service.registry.model.dto.RegisteredInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.model.request.RegistryBody
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.service.RegistryService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegistryServiceImpl(val instancesRepository: InstancesRepository): RegistryService {
    override fun registerInstance(registryBody: RegistryBody): RegisteredInstanceDTO {
        try {
            val instanceUUID: UUID = UUID.randomUUID()
            instancesRepository.save(
                Instance(
                    id = instanceUUID,
                    port = registryBody.port,
                    path = registryBody.contextPath
                )
            )

            return RegisteredInstanceDTO(instanceUUID.toString())
        } catch (exc: Exception) {
            exc.printStackTrace()
            throw RegistryException(ErrorCode.ERR_SAVE_INSTANCE_TO_DB_EXCEPTION)
        }
    }
}
