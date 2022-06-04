package com.monitoring.microservices.service.registry.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "targets", schema = "service_registry")
data class Target(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "url")
    val url: String = "",

    @ManyToOne
    @JsonBackReference
    val instance: Instance? = null
)
