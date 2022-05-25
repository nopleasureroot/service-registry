package com.monitoring.microservices.service.registry.model.entity

import javax.persistence.*

@Entity
@Table(name = "targets", schema = "service_registry")
data class Target(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "url")
    val url: String = "",

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instance_uuid")
    val instance: Instance? = null
)
