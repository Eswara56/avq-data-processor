package com.maybank.data;

import java.util.Objects;

public class CompanyCodeKey {
    private String COM_COA_SEG1;
    private String COM_UNIT_SEG2;

    public CompanyCodeKey(String COM_COA_SEG1, String COM_UNIT_SEG2) {
        this.COM_COA_SEG1 = COM_COA_SEG1;
        this.COM_UNIT_SEG2 = COM_UNIT_SEG2;
    }

    public String getCOM_COA_SEG1() {
        return COM_COA_SEG1;
    }

    public void setCOM_COA_SEG1(String COM_COA_SEG1) {
        this.COM_COA_SEG1 = COM_COA_SEG1;
    }

    public String getCOM_UNIT_SEG2() {
        return COM_UNIT_SEG2;
    }

    public void setCOM_UNIT_SEG2(String COM_UNIT_SEG2) {
        this.COM_UNIT_SEG2 = COM_UNIT_SEG2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyCodeKey that = (CompanyCodeKey) o;
        return Objects.equals(COM_COA_SEG1, that.COM_COA_SEG1) && Objects.equals(COM_UNIT_SEG2, that.COM_UNIT_SEG2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(COM_COA_SEG1, COM_UNIT_SEG2);
    }
}
