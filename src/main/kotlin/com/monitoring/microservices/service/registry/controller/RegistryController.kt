package com.monitoring.microservices.service.registry.controller

import com.monitoring.microservices.service.registry.model.dto.RegisteredInstanceDTO
import com.monitoring.microservices.service.registry.model.request.RegistryBody
import com.monitoring.microservices.service.registry.service.impl.RegistryServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class RegistryController(val registryServiceImpl: RegistryServiceImpl) {
    @PostMapping("/registration/new", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerNewInstance(@RequestBody registryBody: RegistryBody) : ResponseEntity<RegisteredInstanceDTO> =
        ResponseEntity(registryServiceImpl.registerNewInstance(registryBody), HttpStatus.ACCEPTED)
}
