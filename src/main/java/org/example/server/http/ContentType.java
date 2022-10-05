package org.example.server.http;

public enum ContentType {
    TEXT_PLAIN("text/plain")
    ;


    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String contentType;
}
