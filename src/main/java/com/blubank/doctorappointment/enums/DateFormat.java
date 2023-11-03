package com.blubank.doctorappointment.enums;

public enum DateFormat {

    BASIC_DATE("yyyy/MM/dd"),
    BASIC_DATE_DASH("yyyy-MM-dd"),
    BASIC_DATE_TIME("yyyy/MM/dd hh:mm:ss"),
    BASIC_DATE_TIME_DASH("yyyy-MM-dd hh:mm:ss"),
    BASIC_DATE_TIME_GREGORIAN("yyyy-MM-dd hh:mm:ss"),
    TIME("hh:mm:ss");

    private String key;

    DateFormat(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

