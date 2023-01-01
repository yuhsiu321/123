package org.example.application.Gaming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.CardRepository;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CardController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    Gson gson;

    public CardController(CardRepository cardRepository, UserRepository userRepository) {
        gson = new Gson();
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Response handle(Request request){
        if(request.getMethod().equals(Method.GET.method)){
            if (request.getToken() == null ) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return readAll(request);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response readAll(Request request) {
        User user ;
        user = userRepository.findbyUsername(request.getToken());

        List<Card> cards = cardRepository.getCardsForUser(user);
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContent(gson.toJson(cards)+"\n");

        return response;
    }
}
