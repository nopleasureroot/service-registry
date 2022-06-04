package com.monitoring.microservices.service.registry.service

import com.monitoring.microservices.service.registry.model.entity.Instance

interface LoadBalancerService {
    fun findFreeInstance(): Instance?
}
