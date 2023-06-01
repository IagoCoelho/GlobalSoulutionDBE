package com.globalSolution.api.models;

public record RestValidationError(
        Integer code,
        String field, 
        String message) {
    
}
