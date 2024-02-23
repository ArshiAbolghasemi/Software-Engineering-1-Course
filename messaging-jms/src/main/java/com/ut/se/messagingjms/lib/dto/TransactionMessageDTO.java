package com.ut.se.messagingjms.lib.dto;

import java.io.Serial;
import java.io.Serializable;

public class TransactionMessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String command;
    private String[] args;

    public TransactionMessageDTO() {}

    public TransactionMessageDTO(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() { return this.command; }

    public void setCommand(String command) { this.command = command; }

    public String[] getArgs() { return this.args; }

    public void setArgs(String[] args) { this.args = args; }

    @Override
    public String toString() {
        return String.format(
                "%s %s}",
                this.command,
                String.join(" ", this.args)
        );
    }
}
