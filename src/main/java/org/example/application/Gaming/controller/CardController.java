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

public class CardController {

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Response handle(Request request){
        if(request.getMethod().equals(Method.POST.method)){
            if (request.getAuthUser() == null || !"admin".equalsIgnoreCase(request.getAuthUser().getUsername())) {
                return null;
            }else {
                //User user = (User) request.getAuthUser();
                return create(request);
            }
        }

        if (request.getMethod().equals(Method.GET.method)) {
            return readAll();
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return null;
    }

    private Response readAll() {
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization("Basic admin-mtcgtoken");
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

        card = cardRepository.save(card);

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

}
