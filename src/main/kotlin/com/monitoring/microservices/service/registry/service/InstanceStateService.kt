package com.monitoring.microservices.service.registry.service

import com.monitoring.microservices.service.registry.model.InstanceStatus
import java.util.*

interface InstanceStateService {
    fun changeInstanceStatusState(uuid: UUID, status: InstanceStatus)
    fun changeFullInstanceState(uuid: UUID)
}
