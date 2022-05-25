package com.monitoring.microservices.service.registry.controller

import com.monitoring.microservices.service.registry.model.response.RegisteredInstanceResponse
import com.monitoring.microservices.service.registry.service.RegistryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistryController(val registryService: RegistryService) {
    @PostMapping("/registration")
    fun registerInstance() : ResponseEntity<RegisteredInstanceResponse> {
        return ResponseEntity(registryService.registerInstance(), HttpStatus.ACCEPTED)
    }
}
