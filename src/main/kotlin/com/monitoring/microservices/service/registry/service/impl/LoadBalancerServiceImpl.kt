package com.monitoring.microservices.service.registry.service.impl

import com.monitoring.microservices.service.registry.model.entity.Instance
import com.monitoring.microservices.service.registry.repository.InstancesRepository
import com.monitoring.microservices.service.registry.service.LoadBalancerService
import org.springframework.stereotype.Service

@Service
class LoadBalancerServiceImpl(val instancesRepository: InstancesRepository): LoadBalancerService {
    override fun findFreeInstance(): Instance? =
        instancesRepository.findAll().minByOrNull { it.targets.size }
}
