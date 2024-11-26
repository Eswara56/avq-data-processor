package com.maybank.repository;

import com.maybank.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
