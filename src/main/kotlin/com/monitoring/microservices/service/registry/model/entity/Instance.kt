package com.monitoring.microservices.service.registry.model.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "instances", schema = "service_registry")
data class Instance (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
)
