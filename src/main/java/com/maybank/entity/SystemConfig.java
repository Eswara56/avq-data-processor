package com.maybank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "T_EGL_BASE_SYSTEMS")
public class SystemConfig {

	@Id
    @Column(name = "S_NO") // Assuming S_NO is a unique identifier for each row
    private Integer sNo;
    @Column(name = "APPL_CODE") 
    private String applCode;

    @Column(name = "HEADER_FILE")
    private String headerFile;

    @Column(name = "DETAIL_FILE")
    private String detailFile;

    @Column(name = "FOOTER_FILE")
    private String footerFile;
    
    @Column(name = "TBL_HDR")
    private String TBL_HDR;
    
    @Column(name = "TBL_DTL")
    private String TBL_DTL;
    
    @Column(name = "TBL_TRLR")
    private String TBL_TRLR;
    
    public Integer getsNo() {
		return sNo;
	}

	public void setsNo(Integer sNo) {
		this.sNo = sNo;
	}
	public String getApplCode() {
		return applCode;
	}

	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}

	public String getHeaderFile() {
		return headerFile;
	}

	public void setHeaderFile(String headerFile) {
		this.headerFile = headerFile;
	}

	public String getDetailFile() {
		return detailFile;
	}

	public void setDetailFile(String detailFile) {
		this.detailFile = detailFile;
	}

	public String getFooterFile() {
		return footerFile;
	}

	public void setFooterFile(String footerFile) {
		this.footerFile = footerFile;
	}

	public String getTBL_HDR() {
		return TBL_HDR;
	}

	public void setTBL_HDR(String tBL_HDR) {
		TBL_HDR = tBL_HDR;
	}

	public String getTBL_DTL() {
		return TBL_DTL;
	}

	public void setTBL_DTL(String tBL_DTL) {
		TBL_DTL = tBL_DTL;
	}

	public String getTBL_TRLR() {
		return TBL_TRLR;
	}

	public void setTBL_TRLR(String tBL_TRLR) {
		TBL_TRLR = tBL_TRLR;
	}

	

}