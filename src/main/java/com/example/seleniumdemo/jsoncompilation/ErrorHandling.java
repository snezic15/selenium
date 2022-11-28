package com.example.seleniumdemo.jsoncompilation;

import java.util.Map;

public class ErrorHandling {
    private int index;
    private Map<String, Object> inputs;
    private String error;
    private String method;
    private String file;

    public ErrorHandling (int index, Map<String, Object> inputs, Exception e) {
        this.index = index;
        this.inputs = inputs;

        error = e.getMessage();
        method = e.getStackTrace()[0].getMethodName();
        file = e.getStackTrace()[0].getFileName();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Map<String, Object> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
