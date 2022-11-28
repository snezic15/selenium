package com.example.seleniumdemo.jsoncompilation.jsonbody;


import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.constraints.NotNull;

public class JsonBodyInputAdvocateName {
    @NotNull
    private String state_val;
    @NotNull
    private String district_val;
    @NotNull
    private String courtComplexEstb;
    @NotNull
    private String bench_val;
    @NotNull
    private String name;
    @NotNull
    private String f;
    @NotNull
    private String recordsReturned;
    @NotNull
    private String format = "0";

    public String getState_val() {
        return state_val;
    }

    public void setState_val(String state_val) {
        this.state_val = state_val;
    }

    public String getDistrict_val() {
        return district_val;
    }

    public void setDistrict_val(String district_val) {
        this.district_val = district_val;
    }

    public String getCourtComplexEstb() {
        return courtComplexEstb;
    }

    public void setCourtComplexEstb(String courtComplexEstb) {
        this.courtComplexEstb = courtComplexEstb;
    }

    public String getBench_val() {
        return bench_val;
    }

    public void setBench_val(String bench_val) {
        this.bench_val = bench_val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public int getRecordsReturned() {
        return Integer.parseInt(recordsReturned);
    }

    public void setRecordsReturned(String recordsReturned) {
        if (NumberUtils.isCreatable(recordsReturned)) {
            if (Integer.parseInt(recordsReturned) == 0) this.recordsReturned = "999";
            else this.recordsReturned = recordsReturned;
        } else if (recordsReturned.trim().equalsIgnoreCase("")) this.recordsReturned = "999";
        else this.recordsReturned = String.valueOf(1);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}