package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.exception.ErrorCode
import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.exception.InstanceException
import com.monitoring.microservices.service.registry.exception.RegistryException
import com.monitoring.microservices.service.registry.model.request.LaunchBody
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.service.InstanceService
import com.monitoring.microservices.service.registry.util.Constants
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.http.HttpResponse

@Service
class InstanceServiceImpl (
    private val loadBalancerServiceImpl: LoadBalancerServiceImpl,
    private val messengerServiceImpl: MessengerServiceImpl<LaunchBody>,
    private val registryServiceImpl: RegistryServiceImpl,
    private val instancesRepository: InstancesRepository
): InstanceService
{
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun addTarget(launchedInstanceDTO: LaunchedInstanceDTO): LaunchedInstanceDTO? {
        val freeInstance: Instance? = loadBalancerServiceImpl.findFreeInstance()

        if (freeInstance !== null) {
            logger.info("The free instance has been found: ${freeInstance.id}")
            if (freeInstance.path != null && freeInstance.path.isNotEmpty() && freeInstance.port != 0) {
                val launchBody = LaunchBody(
                    targets = launchedInstanceDTO.targets,
                    contextPath = freeInstance.path,
                    port = freeInstance.port
                )

                if (checkInstanceHealth(launchBody.port, launchBody.contextPath, freeInstance)) {
                    val response: HttpResponse<String>? = messengerServiceImpl.executePostRequest(
                        launchBody,
                        url = "${Constants.DEV_URL_PATH}${launchBody.port}${launchBody.contextPath}/targets"
                    )

                    if (response !== null
                        && response.statusCode() == 201
                    ) {
                        try {
                            registryServiceImpl.registerInstanceTargets(launchedInstanceDTO, freeInstance)

                            return launchedInstanceDTO
                        } catch (exc: RegistryException) {
                            exc.printStackTrace()

                            throw exc
                        }
                    }
                } else {
                    throw InstanceException(ErrorCode.ERR_INSTANCE_UNAVAILABLE_NOW_EXCEPTION)
                }
            }

            throw InstanceException(ErrorCode.ERR_INCONSISTENT_INSTANCE_STATE_EXCEPTION)
        } else {
            logger.error("Free instance was not found, please try again later")
            throw InstanceException(ErrorCode.ERR_NOT_FOUND_FREE_INSTANCE_EXCEPTION)
        }

    }

    override fun checkInstanceHealth(port: Int, contextPath: String, instance: Instance): Boolean {
        try {
            val response: HttpResponse<String>? =  messengerServiceImpl.executeGetRequest(
                url = "${Constants.DEV_URL_PATH}${port}${contextPath}/actuator/health"
            )

            return response != null && response.statusCode() == 200
        } catch (exc: Exception) {
            exc.printStackTrace()

            instance.status = "OFFLINE"
            instancesRepository.save(instance)

            throw exc
        }
    }
}
