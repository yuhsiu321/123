package org.example.application.Gaming.controller;

import com.google.gson.Gson;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.CardRepository;
import org.example.application.Gaming.respository.DeckRepository;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.List;

public class DeckControler {
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;

    Gson gson;

    public DeckControler(DeckRepository deckRepository,UserRepository userRepository) {
        gson = new Gson();
        this.deckRepository = deckRepository;
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
        } else if (request.getMethod().equals(Method.PUT.method)) {
            if (request.getToken() == null ) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return put(request);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response readAll(Request request){
        User user;
        user = userRepository.findbyUsername(request.getToken());
        List<Card> cards = deckRepository.getDeck(user);
        String returnBody;
        if(request.getPath().endsWith("?format=plain")){
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Deck: ");
            for (Card card : cards) {
                stringBuilder
                        .append(card.getName())
                        .append("(")
                        .append(card.getElementType())
                        .append(", ")
                        .append(card.getCardType())
                        .append(", ")
                        .append(card.getDamage())
                        .append("), ");
            }

            stringBuilder.setLength(stringBuilder.length() - 2);

            returnBody = stringBuilder.toString();
        }else{
            returnBody = gson.toJson(cards);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContent(returnBody);

        return response;
    }

    public Response put(Request request){
        User user;
        user = userRepository.findbyUsername(request.getToken());
        String[] ids = gson.fromJson(request.getContent(), String[].class);
        System.out.println(ids.toString());
        boolean result = deckRepository.addCardsWithIdsToDeck(ids, user);
        if(result){
            List<Card> cards = deckRepository.getDeck(user);
            Response response = new Response();
            response.setStatusCode(StatusCode.OK);
            response.setContent(gson.toJson(cards));
            return response;
        }else {
            Response response = new Response();
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setContent("Error");
            return response;
        }
    }

}
