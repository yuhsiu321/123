package org.example.server.dto;

public class Response {

    private int status;
    private String message;

    private String contentType;
    private String content = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return
                "HTTP/1.1 " + status + " " + message + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + content.length() + "\r\n" +
                "\r\n" +
                content;
    }
}
