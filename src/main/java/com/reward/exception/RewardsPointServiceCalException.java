package com.reward.exception;

public class RewardsPointServiceCalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RewardsPointServiceCalException(Object resourId) {
        super(resourId != null ? resourId.toString() : null);
    }
}
