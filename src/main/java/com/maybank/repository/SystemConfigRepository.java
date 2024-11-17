package com.maybank.repository;
import com.maybank.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {
    SystemConfig findByApplCode(String applCode); 
}
