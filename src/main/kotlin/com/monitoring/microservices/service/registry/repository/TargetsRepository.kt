package com.monitoring.microservices.service.registry.repository

import com.monitoring.microservices.service.registry.model.entity.Target
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TargetsRepository : JpaRepository<Target, Long> {
}
