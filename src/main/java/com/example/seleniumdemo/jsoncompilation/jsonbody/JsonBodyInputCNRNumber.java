package com.example.seleniumdemo.jsoncompilation.jsonbody;

import javax.validation.constraints.NotNull;

public class JsonBodyInputCNRNumber {
    @NotNull
    private String cino;

    public String getCino() {
        return cino;
    }

    public void setCino(String cino) {
        this.cino = cino;
    }
}