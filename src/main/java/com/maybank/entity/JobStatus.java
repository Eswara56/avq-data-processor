package com.maybank.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Entity
@Table(name = "T_EGL_JOB_STATUS")
public class JobStatus {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    private Long JOB_ID;
    @Column(name = "JOBS_DESC")
    private String JOBS_DESC;
    @Column(name = "SYSTEM_ID")
    private String SYSTEM_ID;
    @Column(name = "JOB_NAME")
    private String JOB_NAME;
    @Column(name = "STATUS_LEVEL")
    private Integer STATUS_LEVEL;
    @Column(name = "FUNC_NAME")
    private String FUNC_NAME;
    @Column(name = "FUNC_DESC")
    private String FUNC_DESC;
    @Column(name = "EFF_DATE")
    private Date EFF_DATE;
    @UpdateTimestamp
    @Column(name = "JOB_TIME")
    private Timestamp JOB_TIME;

    public Long getJOB_ID() {
        return JOB_ID;
    }

    public void setJOB_ID(Long JOB_ID) {
        this.JOB_ID = JOB_ID;
    }

    public String getJOBS_DESC() {
        return JOBS_DESC;
    }

    public void setJOBS_DESC(String JOBS_DESC) {
        this.JOBS_DESC = JOBS_DESC;
    }

    public String getSYSTEM_ID() {
        return SYSTEM_ID;
    }

    public void setSYSTEM_ID(String SYSTEM_ID) {
        this.SYSTEM_ID = SYSTEM_ID;
    }

    public String getJOB_NAME() {
        return JOB_NAME;
    }

    public void setJOB_NAME(String JOB_NAME) {
        this.JOB_NAME = JOB_NAME;
    }

    public Integer getSTATUS_LEVEL() {
        return STATUS_LEVEL;
    }

    public void setSTATUS_LEVEL(Integer STATUS_LEVEL) {
        this.STATUS_LEVEL = STATUS_LEVEL;
    }

    public String getFUNC_NAME() {
        return FUNC_NAME;
    }

    public void setFUNC_NAME(String FUNC_NAME) {
        this.FUNC_NAME = FUNC_NAME;
    }

    public String getFUNC_DESC() {
        return FUNC_DESC;
    }

    public void setFUNC_DESC(String FUNC_DESC) {
        this.FUNC_DESC = FUNC_DESC;
    }

    public Date getEFF_DATE() {
        return EFF_DATE;
    }

    public void setEFF_DATE(Date EFF_DATE) {
        this.EFF_DATE = EFF_DATE;
    }

    public Timestamp getJOB_TIME() {
        return JOB_TIME;
    }

    public void setJOB_TIME(Timestamp JOB_TIME) {
        this.JOB_TIME = JOB_TIME;
    }
}
