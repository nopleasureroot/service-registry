package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.exception.ErrorCode
import com.monitoring.microservices.service.registry.exception.RegistryException
import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO
import com.monitoring.microservices.service.registry.model.dto.RegisteredInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.model.entity.Target
import com.monitoring.microservices.service.registry.model.request.RegistryBody
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.repository.TargetsRepository
import com.monitoring.microservices.service.registry.service.RegistryService
import com.monitoring.microservices.service.registry.util.ValidationUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class RegistryServiceImpl(
    private val instancesRepository: InstancesRepository,
    private val targetsRepository: TargetsRepository
    ): RegistryService {
    override fun registerNewInstance(registryBody: RegistryBody): RegisteredInstanceDTO {
        try {
            val instance = Instance(
                port = registryBody.port,
                path = registryBody.contextPath,
                lastLaunch = LocalDateTime.now(),
                status = "ONLINE"
            )
            instancesRepository.save(instance)

            return RegisteredInstanceDTO(instance.id.toString())
        } catch (exc: Exception) {
            exc.printStackTrace()
            throw RegistryException(ErrorCode.ERR_SAVE_INSTANCE_TO_DB_EXCEPTION)
        }
    }

    override fun registerInstanceTargets(launchedInstanceDTO: LaunchedInstanceDTO, instance: Instance) {
        try {
            launchedInstanceDTO.targets.forEach {
                if (ValidationUtils.validateURL(it.url)) {
                    targetsRepository.save(Target(null, it.url, instance))
                }
            }
        } catch (exc: Exception) {
            throw RegistryException(ErrorCode.ERR_REGISTRY_INSTANCE_TARGETS_EXCEPTION)
        }
    }


}
