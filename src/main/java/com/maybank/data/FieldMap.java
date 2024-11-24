package com.maybank.data;

import java.util.Objects;

/**
 * This class holds the column name and
 */
public class FieldMap {

    private String column;
    private String value;
    private  String type;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FieldMap{" +
                "column='" + column + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldMap fieldMap = (FieldMap) o;
        return Objects.equals(column, fieldMap.column) && Objects.equals(value, fieldMap.value) && Objects.equals(type, fieldMap.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, value, type);
    }
}
