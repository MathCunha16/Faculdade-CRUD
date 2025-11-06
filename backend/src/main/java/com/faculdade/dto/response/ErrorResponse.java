package com.faculdade.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldError> errors
) {
    public record FieldError(
            String field,
            String message
    ) {
    }
}


