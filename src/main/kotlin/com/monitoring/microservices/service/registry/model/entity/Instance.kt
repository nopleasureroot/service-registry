package com.monitoring.microservices.service.registry.model.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "instances", schema = "service_registry")
data class Instance (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(name = "instance_port")
    val port: Int = 0,

    @Column(name = "instance_path")
    val path: String? = null,

    @Column(name = "last_launch_time")
    var lastLaunch: LocalDateTime? = null,

    @Column(name = "status")
    var status: String? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "instance")
    @JsonManagedReference
    val targets: List<Target> = mutableListOf()
)
