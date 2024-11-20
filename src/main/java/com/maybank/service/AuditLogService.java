package com.maybank.service;

import com.maybank.entity.AuditLog;
import com.maybank.repo.AuditLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Async
    public void log(String appName, String level, String message, String threadName, String loggerName, String stackTrace) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAppName(appName);
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setLogLevel(level);
        auditLog.setMessage(message);
        auditLog.setThreadName(threadName);
        auditLog.setLoggerName(loggerName);
        auditLog.setStackTrace(stackTrace);
        auditLogRepository.save(auditLog);
    }
}
