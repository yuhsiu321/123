package org.example.application.Gaming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.CardRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;
import org.jetbrains.annotations.NotNull;

public class CardController {

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Response handle(Request request){
        if (request.getToken() == null || !"admin".equalsIgnoreCase(request.getToken())) {
            Response response = new Response();
            response.setStatusCode(StatusCode.UNAUTHORIZED);
            response.setContentType(ContentType.TEXT_PLAIN);
            response.setContent(StatusCode.UNAUTHORIZED.message);
            return response;
        }
        if(request.getMethod().equals(Method.POST.method)){
                //User user = new User();
                //user.setUsername(request.getToken());
                return create(request);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response readAll() {
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        //response.setAuthorization("username");
        String content = null;
        try {
            content = objectMapper.writeValueAsString(cardRepository.findAll());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

    private Response create(Request request){
        ObjectMapper objectMapper = new ObjectMapper();

        String json = request.getContent();
        Card card;
        try {
            card = objectMapper.readValue(json, Card.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        card = cardRepository.save(card, request.getToken());

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        //response.setAuthorization("Basic "+username+"-mtcgToken");
        //response.setAuthorization("Basic admin-mtcgToken");
        String content;
        try {
            content = objectMapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

}
