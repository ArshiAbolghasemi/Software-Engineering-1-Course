package com.ut.se.messagingjms.lib.dto;

import java.io.Serializable;

public class SystemResponseDTO implements Serializable {

    private int code;

    private String action;

    private String context;

    public SystemResponseDTO() {}

    public SystemResponseDTO(int code, String action, String context) {
        this.code = code;
        this.action = action;
        this.context = context;
    }

    public int getCode() { return this.code; }

    public void setCode(int code) { this.code = code; }

    public String getAction() { return this.action; }

    public void setAction(String action) { this.action = action; }

    public String getContext() { return this.context; }

    public void setContext(String context) { this.context = context; }

    @Override
    public String toString() { return String.format("%d %s %s", this.code, this.action, this.context); }
}
