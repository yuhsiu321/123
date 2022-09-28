package org.example.application.demo;

import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;

public class DemoApp implements Application {

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        response.setStatus(200);
        response.setMessage("OK");
        response.setContentType("text/plain");
        response.setContent(request.getRequest());

        return response;
    }
}
