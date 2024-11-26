package com.maybank.service;

import com.maybank.entity.AuditLog;
import com.maybank.repository.AuditLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Async
    public void log( String FUN_NAME, String REP_NAME, String USER_ID, String ACTION_INFO, String ERR_EXP_MESSAGE) {
        AuditLog auditLog = new AuditLog();
        auditLog.setJOB_DATE(LocalDateTime.now());
        auditLog.setFUN_NAME(FUN_NAME);
        auditLog.setUSER_ID(USER_ID);
        auditLog.setACTION_INFO(ACTION_INFO);
        auditLog.setERR_EXP_MESSAGE(ERR_EXP_MESSAGE);
        auditLog.setSTATUS("ERROR");
        auditLog.setREP_NAME(REP_NAME);
        auditLogRepository.save(auditLog);
    }
}
