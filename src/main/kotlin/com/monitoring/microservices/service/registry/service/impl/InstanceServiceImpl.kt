package com.monitoring.microservices.service.registry.service.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.monitoring.microservices.service.registry.exception.ErrorCode
import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.exception.InstanceException
import com.monitoring.microservices.service.registry.model.request.LaunchBody
import com.monitoring.microservices.service.registry.service.InstanceService
import com.monitoring.microservices.service.registry.util.Constants
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.http.HttpResponse

@Service
class InstanceServiceImpl (
    val loadBalancerServiceImpl: LoadBalancerServiceImpl,
    val messengerServiceImpl: MessengerServiceImpl<LaunchBody>
): InstanceService
{
    private val logger = LoggerFactory.getLogger(javaClass)
    private val mapper = jacksonObjectMapper()

    override fun addTarget(launchedInstanceDTO: LaunchedInstanceDTO): LaunchedInstanceDTO? {
        val freeInstance: Instance? = loadBalancerServiceImpl.findFreeInstance()

        if (freeInstance !== null) {
            logger.info("The free instance has been found: ${freeInstance.id}")
            if (freeInstance.path != null && freeInstance.path.isNotEmpty() && freeInstance.port != 0) {
                val launchBody = LaunchBody(
                    targets = launchedInstanceDTO.targets,
                    contextPath = freeInstance.path,
                    freeInstance.port
                )

                val response: HttpResponse<String>? = messengerServiceImpl.executePostRequest(
                    launchBody,
                    url = "${Constants.DEV_URL_PATH}${launchBody.port}${launchBody.contextPath}"
                )

                return mapper.readValue<LaunchedInstanceDTO>(
                    response?.body() ?:
                    throw InstanceException(ErrorCode.ERR_INSTANCE_UNAVAILABLE_NOW_EXCEPTION)
                )
            }

            throw InstanceException(ErrorCode.ERR_INCONSISTENT_INSTANCE_STATE_EXCEPTION)
        } else {
            logger.error("Free instance was not found, please try again later")
            throw InstanceException(ErrorCode.ERR_NOT_FOUND_FREE_INSTANCE_EXCEPTION)
        }

    }
}
