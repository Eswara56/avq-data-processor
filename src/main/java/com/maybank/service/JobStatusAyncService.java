package com.maybank.service;

import com.maybank.entity.JobStatus;
import com.maybank.repository.JobStatusRepository;
import com.maybank.util.ApplicationUtil;
import com.maybank.util.JobStatusMap;
import com.maybank.util.Status;
import com.maybank.util.StatusMessageType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is used to store the detail data into {@link JobStatus}
 */
@Service
public class JobStatusAyncService {

    private final JobStatusRepository jobStatusRepository;

    public JobStatusAyncService(JobStatusRepository jobStatusRepository) {
        this.jobStatusRepository = jobStatusRepository;
    }

    private void insertOrUpdateJobStatus(JobStatus inputJobsStatus) {
        jobStatusRepository.save(inputJobsStatus);
    }

    @Async
    public void updateJobStatusMapAndInsertIntoDB(String appCode, Status status, JobStatus jobStatus) {
        JobStatusMap.updateValue(appCode, ApplicationUtil.currentMethodName(), new Status(StatusMessageType.IN_PROGRESS.getValue(), 10.0));
        insertOrUpdateJobStatus(jobStatus);
    }

    public boolean checkJobCompletedStatus(String appCode) {
        Status status = JobStatusMap.getPercentage(appCode);
        return status.getMessageType().equals(StatusMessageType.COMPLETED.getValue());
    }
}
