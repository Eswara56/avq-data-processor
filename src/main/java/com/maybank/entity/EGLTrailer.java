package org.maybank.com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_EGL_UPSTREAM_CONFIG")
public class EGLTrailer {
	@Id
	@Column(name = "UP_STREAM_ORDER")
	private Integer UP_STREAM_ORDER;
	@Column(name = "SYSTEM_ID")
	private String SYSTEM_ID;
	@Column(name = "FILE_TYPE")
	private String FILE_TYPE;
	@Column(name = "FIELD_NAME")
	private String FIELD_NAME;
	@Column(name = "FIELD_LENGTH")
	private Integer FIELD_LENGTH;
	@Column(name = "FIELD_START")
	private Integer FIELD_START;
	public Integer getUP_STREAM_ORDER() {
		return UP_STREAM_ORDER;
	}
	public void setUP_STREAM_ORDER(Integer uP_STREAM_ORDER) {
		UP_STREAM_ORDER = uP_STREAM_ORDER;
	}
	public String getSYSTEM_ID() {
		return SYSTEM_ID;
	}
	public void setSYSTEM_ID(String sYSTEM_ID) {
		SYSTEM_ID = sYSTEM_ID;
	}
	public String getFILE_TYPE() {
		return FILE_TYPE;
	}
	public void setFILE_TYPE(String fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}
	public String getFIELD_NAME() {
		return FIELD_NAME;
	}
	public void setFIELD_NAME(String fIELD_NAME) {
		FIELD_NAME = fIELD_NAME;
	}
	public Integer getFIELD_LENGTH() {
		return FIELD_LENGTH;
	}
	public void setFIELD_LENGTH(Integer fIELD_LENGTH) {
		FIELD_LENGTH = fIELD_LENGTH;
	}
	public Integer getFIELD_START() {
		return FIELD_START;
	}
	public void setFIELD_START(Integer fIELD_START) {
		FIELD_START = fIELD_START;
	}
}
