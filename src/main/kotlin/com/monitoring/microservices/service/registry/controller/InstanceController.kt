package com.monitoring.microservices.service.registry.controller

import com.monitoring.microservices.service.registry.model.dto.LaunchedInstanceDTO
import com.monitoring.microservices.service.registry.service.impl.InstanceServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/instances")
class InstanceController(val instanceServiceImpl: InstanceServiceImpl) {
    @PostMapping()
    fun addTargetToInstance(@RequestBody launchedInstanceDTO: LaunchedInstanceDTO): ResponseEntity<LaunchedInstanceDTO>? =
        ResponseEntity(instanceServiceImpl.addTarget(launchedInstanceDTO), HttpStatus.CREATED)
}
