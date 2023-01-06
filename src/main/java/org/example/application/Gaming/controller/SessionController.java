package org.example.application.Gaming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class SessionController {

    private final UserRepository userRepository;

    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response handle(Request request){
        if (request.getMethod().equals(Method.POST.method)) {
            return read(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }

    private Response read(Request request){
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
        User user;

        try {
            user = objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content;
        try {
            userRepository.login(user);
            userRepository.updateUsertoLogin(user);
            content = objectMapper.writeValueAsString(userRepository.login(user));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if(content.equals("null")){
            content="false username or password";
            response.setContent(content);
        }else {
            content = "login successfully";
            response.setContent(content);
        }
        return response;

    }

}
