package org.example.server.http;

public enum StatusCode {
    OK(200, "OK"),
    CREATED(201, "Created"),

    BAD_REQUEST(400,"Bad Request"),
    UNAUTHORIZED(401,"Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    METHODE_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
    ;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code;

    public String message;
}
