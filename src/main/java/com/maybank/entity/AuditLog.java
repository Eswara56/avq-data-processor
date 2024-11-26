package com.maybank.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Entity
@Table(name = "T_EGL_EX_BACKEND_LOG")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer JOB_ID;
    @Column(name="JOB_DATE")
    private LocalDateTime JOB_DATE;
    @Column(name="FUN_NAME")
    private String FUN_NAME;
    @Column(name="USER_ID")
    private  String USER_ID;
    @Column(name="ACTION_INFO")
    private String ACTION_INFO;
    @Column(name="ERR_EXP_MESSAGE")
    private String ERR_EXP_MESSAGE;
    @Column(name="STATUS")
    private String STATUS;
    @Column(name="PROC_DATE")
    private LocalDateTime PROC_DATE;
    @Column(name="REP_NAME")
    private String REP_NAME;

    public Integer getJOB_ID() {
        return JOB_ID;
    }

    public void setJOB_ID(Integer JOB_ID) {
        this.JOB_ID = JOB_ID;
    }

    public LocalDateTime getJOB_DATE() {
        return JOB_DATE;
    }

    public void setJOB_DATE(LocalDateTime JOB_DATE) {
        this.JOB_DATE = JOB_DATE;
    }

    public String getFUN_NAME() {
        return FUN_NAME;
    }

    public void setFUN_NAME(String FUN_NAME) {
        this.FUN_NAME = FUN_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getACTION_INFO() {
        return ACTION_INFO;
    }

    public void setACTION_INFO(String ACTION_INFO) {
        this.ACTION_INFO = ACTION_INFO;
    }

    public String getERR_EXP_MESSAGE() {
        return ERR_EXP_MESSAGE;
    }

    public void setERR_EXP_MESSAGE(String ERR_EXP_MESSAGE) {
        this.ERR_EXP_MESSAGE = ERR_EXP_MESSAGE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public LocalDateTime getPROC_DATE() {
        return PROC_DATE;
    }

    public void setPROC_DATE(LocalDateTime PROC_DATE) {
        this.PROC_DATE = PROC_DATE;
    }

    public String getREP_NAME() {
        return REP_NAME;
    }

    public void setREP_NAME(String REP_NAME) {
        this.REP_NAME = REP_NAME;
    }
}
