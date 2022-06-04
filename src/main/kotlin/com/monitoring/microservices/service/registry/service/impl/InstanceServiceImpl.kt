package com.monitoring.microservices.service.registry.service.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.model.error.LaunchInstanceErrorResponse
import com.monitoring.microservices.service.registry.model.request.LaunchBody
import com.monitoring.microservices.service.registry.service.InstanceService
import com.monitoring.microservices.service.registry.util.Constants
import lombok.extern.slf4j.Slf4j
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
            val launchBody = LaunchBody(
                targets = launchedInstanceDTO.targets,
                contextPath = freeInstance.path ?:
                throw LaunchInstanceErrorResponse(
                    500,
                    "A free instance is in an inconsistent state"
                ),
                freeInstance.port
            )
            val response: HttpResponse<String>? = messengerServiceImpl.executePostRequest(
                launchBody,
                url = "${Constants.DEV_URL_PATH}${launchBody.port}${launchBody.contextPath}"
            )

            return mapper.readValue<LaunchedInstanceDTO>(
                response?.body() ?:
                throw LaunchInstanceErrorResponse(500, "Instance not available at the moment, try later")
            )
        } else {
            logger.error("Free instance was not found, please try again later")

            throw LaunchInstanceErrorResponse(
                400,
                "Free entity was not found, please try again later"
            )
        }

    }
}
