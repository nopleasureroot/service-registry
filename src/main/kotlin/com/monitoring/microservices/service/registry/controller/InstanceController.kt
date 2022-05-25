package com.monitoring.microservices.service.registry.controller

import com.monitoring.microservices.service.registry.model.response.LaunchedInstanceResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/instances")
class InstanceController {
    @PostMapping("/")
    fun launchInstance(): ResponseEntity<LaunchedInstanceResponse>? {
        return null
    }
}
