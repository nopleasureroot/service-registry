package com.monitoring.microservices.service.registry.controller

import com.monitoring.microservices.service.registry.model.InstanceStatus
import com.monitoring.microservices.service.registry.service.impl.InstanceStateServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class InstanceStateController(val instanceStateServiceImpl: InstanceStateServiceImpl) {
    @GetMapping("/instances/status-state")
    fun changeState(@RequestParam(name = "uuid") uuid: UUID, @RequestParam(name = "status") status: InstanceStatus) : ResponseEntity<Unit> =
        ResponseEntity(instanceStateServiceImpl.changeInstanceStatusState(uuid, status), HttpStatus.OK)

    @GetMapping("/instances/complete-state")
    fun registerExistingInstance(@RequestParam uuid: UUID) : ResponseEntity<Unit> =
        ResponseEntity(instanceStateServiceImpl.changeFullInstanceState(uuid), HttpStatus.OK)
}
