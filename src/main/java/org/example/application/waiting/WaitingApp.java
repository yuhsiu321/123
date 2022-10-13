package org.example.application.waiting;

import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.StatusCode;

public class WaitingApp implements Application {

    @Override
    public Response handle(Request request) {
        System.out.println(Thread.currentThread());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType("text/plain");
        response.setContent(StatusCode.OK.message);
        return response;
    }
}
