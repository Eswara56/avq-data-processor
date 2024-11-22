package com.maybank.service;

import com.maybank.entity.JobStatus;
import com.maybank.repository.JobStatusRepository;
import org.springframework.stereotype.Service;

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
            dbJobStatus.setJOBS_DESC(inputJobsStatus.getJOBS_DESC());
            jobStatusRepository.save(dbJobStatus);
        } else {
            jobStatusRepository.save(inputJobsStatus);
        }
    }
}
