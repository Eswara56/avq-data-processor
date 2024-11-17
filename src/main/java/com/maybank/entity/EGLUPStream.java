package org.maybank.com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="T_EGL_UPSTREAM_CONFIG ")
public class EGLUPStream {
    
	@Id
    @Column(name = "FIELD_ORDER")
    private Integer fieldOrder;
	private String FILE_TYPE;
	private String	FIELD_START;
	private String FIELD_LENGTH;
	private String FIELD_NAME;
	private String SYSTEM_ID;
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
	public String getFIELD_START() {
		return FIELD_START;
	}
	public void setFIELD_START(String fIELD_START) {
		FIELD_START = fIELD_START;
	}
	public String getFIELD_LENGTH() {
		return FIELD_LENGTH;
	}
	public void setFIELD_LENGTH(String fIELD_LENGTH) {
		FIELD_LENGTH = fIELD_LENGTH;
	}
	public String getFIELD_NAME() {
		return FIELD_NAME;
	}
	public void setFIELD_NAME(String fIELD_NAME) {
		FIELD_NAME = fIELD_NAME;
	}
	public Integer getFieldOrder() {
		return fieldOrder;
	}
	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
	@Override
	public String toString() {
		return "EGLUPStream [fieldOrder=" + fieldOrder + ", FILE_TYPE=" + FILE_TYPE + ", FIELD_START=" + FIELD_START
				+ ", FIELD_LENGTH=" + FIELD_LENGTH + ", FIELD_NAME=" + FIELD_NAME + ", SYSTEM_ID=" + SYSTEM_ID + "]";
	}
}
