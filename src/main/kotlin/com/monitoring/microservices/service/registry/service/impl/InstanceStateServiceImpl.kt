package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.model.InstanceStatus
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.service.InstanceStateService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class InstanceStateServiceImpl(
    private val instancesRepository: InstancesRepository
): InstanceStateService {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun changeInstanceStatusState(uuid: UUID, status: InstanceStatus) {
        try {
            val instance: Instance = instancesRepository.findInstanceById(uuid)
            if (instance.id != null) {
                logger.info("Instance was successfully found with id ${instance.id}")

                instance.status = status.name
                instancesRepository.save(instance)
            }
        } catch (exc: Exception) {
            logger.info("Some error occurred")

            throw exc
        }
    }

    override fun changeFullInstanceState(uuid: UUID) {
        try {
            val instance: Instance = instancesRepository.findInstanceById(uuid)
            if (instance.id != null) {
                logger.info("Instance was successfully found with id ${instance.id}")

                instance.status = InstanceStatus.ONLINE.name
                instance.lastLaunch = LocalDateTime.now()
                instancesRepository.save(instance)
            }
        } catch (exc: Exception) {
            logger.info("Some error occurred")

            throw exc
        }
    }
}
