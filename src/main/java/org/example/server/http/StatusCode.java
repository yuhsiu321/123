package org.example.server.http;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found")
    ;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code;

    public String message;
}
