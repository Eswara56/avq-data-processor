package com.maybank.data;

import jakarta.persistence.Table;

//T_EGL_AC_CODE_MAP

public class AcCodeMap {
//  private String  COM_GL_AC_CODE_SEG6;
    private String  COM_GL_AC_DESC;
    private String  EGL_AC_CODE_SEG3;
    private String  EGL_PROD_CODE_SEG4;
    private String  EGL_INT_COM_CODE_SEG5;
    private String  EGL_FUTURE3_SEG6;
    private String  EGL_SUB_AC_SEG7;
    private String  EGL_FUTURE5_SEG8;
    private Character  EGL_REV_DUP;
    private Integer  EGL_CODE_ID;
    private  String STAGE;
    public AcCodeMap(String COM_GL_AC_DESC, String EGL_AC_CODE_SEG3, String EGL_PROD_CODE_SEG4, String EGL_INT_COM_CODE_SEG5, String EGL_FUTURE3_SEG6, String EGL_SUB_AC_SEG7, String EGL_FUTURE5_SEG8, Character EGL_REV_DUP, Integer EGL_CODE_ID,String STAGE) {
        this.COM_GL_AC_DESC = COM_GL_AC_DESC;
        this.EGL_AC_CODE_SEG3 = EGL_AC_CODE_SEG3;
        this.EGL_PROD_CODE_SEG4 = EGL_PROD_CODE_SEG4;
        this.EGL_INT_COM_CODE_SEG5 = EGL_INT_COM_CODE_SEG5;
        this.EGL_FUTURE3_SEG6 = EGL_FUTURE3_SEG6;
        this.EGL_SUB_AC_SEG7 = EGL_SUB_AC_SEG7;
        this.EGL_FUTURE5_SEG8 = EGL_FUTURE5_SEG8;
        this.EGL_REV_DUP = EGL_REV_DUP;
        this.EGL_CODE_ID = EGL_CODE_ID;
        this.STAGE = STAGE;

    }

    //    public String getCOM_GL_AC_CODE_SEG6() {
//        return COM_GL_AC_CODE_SEG6;
//    }
//
//    public void setCOM_GL_AC_CODE_SEG6(String COM_GL_AC_CODE_SEG6) {
//        this.COM_GL_AC_CODE_SEG6 = COM_GL_AC_CODE_SEG6;
//    }

    public String getCOM_GL_AC_DESC() {
        return COM_GL_AC_DESC;
    }

    public void setCOM_GL_AC_DESC(String COM_GL_AC_DESC) {
        this.COM_GL_AC_DESC = COM_GL_AC_DESC;
    }

    public String getEGL_AC_CODE_SEG3() {
        return EGL_AC_CODE_SEG3;
    }

    public void setEGL_AC_CODE_SEG3(String EGL_AC_CODE_SEG3) {
        this.EGL_AC_CODE_SEG3 = EGL_AC_CODE_SEG3;
    }

    public String getEGL_PROD_CODE_SEG4() {
        return EGL_PROD_CODE_SEG4;
    }

    public void setEGL_PROD_CODE_SEG4(String EGL_PROD_CODE_SEG4) {
        this.EGL_PROD_CODE_SEG4 = EGL_PROD_CODE_SEG4;
    }

    public String getEGL_INT_COM_CODE_SEG5() {
        return EGL_INT_COM_CODE_SEG5;
    }

    public void setEGL_INT_COM_CODE_SEG5(String EGL_INT_COM_CODE_SEG5) {
        this.EGL_INT_COM_CODE_SEG5 = EGL_INT_COM_CODE_SEG5;
    }

    public String getEGL_FUTURE3_SEG6() {
        return EGL_FUTURE3_SEG6;
    }

    public void setEGL_FUTURE3_SEG6(String EGL_FUTURE3_SEG6) {
        this.EGL_FUTURE3_SEG6 = EGL_FUTURE3_SEG6;
    }

    public String getEGL_SUB_AC_SEG7() {
        return EGL_SUB_AC_SEG7;
    }

    public void setEGL_SUB_AC_SEG7(String EGL_SUB_AC_SEG7) {
        this.EGL_SUB_AC_SEG7 = EGL_SUB_AC_SEG7;
    }

    public String getEGL_FUTURE5_SEG8() {
        return EGL_FUTURE5_SEG8;
    }

    public void setEGL_FUTURE5_SEG8(String EGL_FUTURE5_SEG8) {
        this.EGL_FUTURE5_SEG8 = EGL_FUTURE5_SEG8;
    }

    public Character getEGL_REV_DUP() {
        return EGL_REV_DUP;
    }

    public void setEGL_REV_DUP(Character EGL_REV_DUP) {
        this.EGL_REV_DUP = EGL_REV_DUP;
    }

    public Integer getEGL_CODE_ID() {
        return EGL_CODE_ID;
    }

    public void setEGL_CODE_ID(Integer EGL_CODE_ID) {
        this.EGL_CODE_ID = EGL_CODE_ID;
    }
}
