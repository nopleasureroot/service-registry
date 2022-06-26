package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.exception.ErrorCode
import com.monitoring.microservices.service.registry.exception.LoadBalancerException
import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.service.LoadBalancerService
import org.springframework.stereotype.Service

@Service
class LoadBalancerServiceImpl(val instancesRepository: InstancesRepository): LoadBalancerService {
    override fun findFreeInstance(): Instance? {
        try {
            return instancesRepository.findInstancesByStatusEquals("ONLINE").minByOrNull { it.targets.size }
        } catch (exc: Exception) {
            exc.printStackTrace()

            throw LoadBalancerException(ErrorCode.ERR_FIND_FREE_INSTANCE_EXCEPTION)
        }
    }
}
