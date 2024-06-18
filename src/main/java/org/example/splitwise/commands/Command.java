package org.example.splitwise.commands;

public interface Command {
    public boolean matches(String input);
    public boolean execute(String input);
}
