package it.spaceschool.ui;

import it.spaceschool.util.LoggerUtil;

import java.util.Scanner;

public class ConsolePrompter {

    private final Scanner scanner;

    public ConsolePrompter(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptRequired(String prompt) {
        while (true) {
            LoggerUtil.info(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isBlank()) {
                return value;
            }
            LoggerUtil.info("Value is required. Please try again.");
        }
    }

    public String promptRaw(String prompt) {
        LoggerUtil.info(prompt);
        return scanner.nextLine();
    }
}
