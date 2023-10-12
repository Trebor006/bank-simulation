package com.test.banksimulation.components.response;

public interface SuccessApiResponseServiceInterface {
    <T> ApiResponse createSuccessResponse(T data);
}
