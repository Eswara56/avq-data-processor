package com.maybank.data;

import java.util.Objects;

public class CreditAndDebit {
    private String CO_CODE;
    private String CURCY_3_CODE;
    private Integer UNIT_NO;

    public CreditAndDebit() {
    }

    public CreditAndDebit(String CO_CODE, String CURCY_3_CODE, Integer UNIT_NO) {
        this.CO_CODE = CO_CODE;
        this.CURCY_3_CODE = CURCY_3_CODE;
        this.UNIT_NO = UNIT_NO;
    }

    public String getCO_CODE() {
        return CO_CODE;
    }
    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
    }

    public String getCURCY_3_CODE() {
        return CURCY_3_CODE;
    }
    public void setCURCY_3_CODE(String CURCY_3_CODE) {
        this.CURCY_3_CODE = CURCY_3_CODE;
    }

    public Integer getUNIT_NO() {
        return UNIT_NO;
    }
    public void setUNIT_NO(Integer UNIT_NO) {
        this.UNIT_NO = UNIT_NO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditAndDebit that = (CreditAndDebit) o;
        return Objects.equals(CO_CODE, that.CO_CODE) && Objects.equals(CURCY_3_CODE, that.CURCY_3_CODE) && Objects.equals(UNIT_NO, that.UNIT_NO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CO_CODE, CURCY_3_CODE, UNIT_NO);
    }
}
