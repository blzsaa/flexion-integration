package com.funflowers.exception;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class IntegratorException extends RuntimeException {
    public IntegratorException(Throwable throwable) {
        super(throwable);
    }

    public IntegratorException(String s) {
        super(s);
    }
}
