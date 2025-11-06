package com.faculdade.util;

import java.util.regex.Pattern;

public class PasswordValidator {

    // Senha deve ter pelo menos:
    // - 8 caracteres
    // - 1 letra maiúscula
    // - 1 letra minúscula
    // - 1 número
    // - 1 caractere especial
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 128;

    public static boolean isValid(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static String getValidationMessage() {
        return String.format(
                "A senha deve ter entre %d e %d caracteres, incluindo pelo menos: " +
                "1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial (@$!%%*?&)",
                MIN_LENGTH, MAX_LENGTH
        );
    }
}

