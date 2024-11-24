package com.maybank.service;

import com.maybank.entity.JobStatus;
import com.maybank.repository.JobStatusRepository;
import jakarta.persistence.Column;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class JobStatusAyncService {

    private final JobStatusRepository jobStatusRepository;

    public JobStatusAyncService(JobStatusRepository jobStatusRepository) {
        this.jobStatusRepository = jobStatusRepository;
    }
    public void insertOrUpdateJobStatus(JobStatus inputJobsStatus) {
        Optional<JobStatus> jobStatusOptional = jobStatusRepository.findBySystemId(inputJobsStatus.getSYSTEM_ID());
        if (jobStatusOptional.isPresent()) {
            //then update
            JobStatus dbJobStatus = jobStatusOptional.get();
            dbJobStatus.setJOB_NAME(inputJobsStatus.getJOB_NAME());
            dbJobStatus.setSTATUS_LEVEL(inputJobsStatus.getSTATUS_LEVEL());
            dbJobStatus.setFUNC_NAME(inputJobsStatus.getFUNC_NAME());
            dbJobStatus.setJOBS_DESC(inputJobsStatus.getJOBS_DESC());
            dbJobStatus.setFUNC_DESC(inputJobsStatus.getFUNC_DESC());
            jobStatusRepository.save(dbJobStatus);
        } else {
            jobStatusRepository.save(inputJobsStatus);
        }
    }
}
