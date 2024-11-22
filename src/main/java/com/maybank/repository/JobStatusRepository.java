package com.maybank.repository;

import com.maybank.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {

    @Query("SELECT j FROM JobStatus j WHERE j.SYSTEM_ID = :systemId")
    Optional<JobStatus> findBySystemId(@Param("systemId") String systemId);
}
