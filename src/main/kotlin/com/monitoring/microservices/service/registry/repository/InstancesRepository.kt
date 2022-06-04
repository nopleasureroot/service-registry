package com.monitoring.microservices.service.registry.repository

import com.monitoring.microservices.service.registry.model.entity.Instance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList

@Repository
interface InstancesRepository : JpaRepository<Instance, UUID> {
    override fun findAll(): ArrayList<Instance>
}
