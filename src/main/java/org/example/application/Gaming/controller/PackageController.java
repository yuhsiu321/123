package org.example.application.Gaming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Package;
import org.example.application.Gaming.respository.CardMemoryRepository;
import org.example.application.Gaming.respository.CardRepository;
import org.example.application.Gaming.respository.PackageMemoryRepository;
import org.example.application.Gaming.respository.PackageRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageController {

    private final PackageRepository packageRepository;

    private final CardRepository cardRepository;

    Gson gson;

    public PackageController(PackageRepository packageRepository, CardRepository cardRepository) {
        gson = new Gson();
        this.packageRepository = packageRepository;
        this.cardRepository = cardRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            //User user = new User();
            //user.setUsername(request.getToken());
            if (request.getToken() == null || !"admin".equalsIgnoreCase(request.getToken())) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return create1(request);
        }


        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response create(Request request) {
        //Package cardPackage = (Package) packageRepository.addPackage(gson.fromJson(request.getContent(), Package.class));
        Package cardPackage = packageRepository.addPackage();

        if (cardPackage != null) {
            //System.out.println("Content:"+request.getContent()+"\n");
            JsonObject jsonObject = JsonParser.parseString(request.getContent()).getAsJsonObject();
            System.out.println("jsonOb:+jsonObject+\n");
            JsonArray jsonArray = jsonObject.getAsJsonArray();
            //System.out.println(jsonArray);
            List<Card> cards = new ArrayList<>();
            //System.out.println("Card:"+cards);
            for (JsonElement cardJsonElement : jsonArray) {
                JsonObject cardJson = cardJsonElement.getAsJsonObject();

                Card card = cardRepository.addCard(new Card (
                        cardJson.get("Id").getAsString(),
                        cardJson.get("Name").getAsString(),
                        cardJson.get("Damage").getAsFloat()
                ));

                card = cardRepository.addCardToPackage(card, cardPackage.getId());

                cards.add(card);
            }

            JsonObject returnJsonObject = (JsonObject) gson.toJsonTree(cardPackage);
            returnJsonObject.add("cards", gson.toJsonTree(cards));

            Response response = new Response();
            response.setStatusCode(StatusCode.CREATED);
            response.setContentType(ContentType.APPLICATION_JSON);
            //response.setAuthorization("Basic "+username+"-mtcgToken");
            response.setAuthorization("Basic admin-mtcgToken");
            response.setContent(gson.toJson(returnJsonObject));
            //response.setContent(StatusCode.CREATED.message);

            return response;
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response create1(Request request) {
        Gson gson = new Gson();
        Package cardPackage = packageRepository.addPackage();
        Card[] cards = gson.fromJson(request.getContent(),Card[].class);
        for(Card card:cards){
            card = cardRepository.addCard(card);
            card = cardRepository.addCardToPackage(card,cardPackage.getId());
            System.out.println(card);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent("cards create");
        return response;
    }

    private Response create2(Request request){

        //Package cardPackage = (Package) packageRepository.addPackage(gson.fromJson(request.getContent(), Package.class));
        ObjectMapper objectMapper = new ObjectMapper();
        Package cardPackage = packageRepository.addPackage();
        System.out.println(cardPackage.getId());

        String json = request.getContent();
        System.out.println("Content:"+json+"\n");
        Card card;
        try {
            //card = objectMapper.readValue(json, Card.class);
            List<Card> cards = Arrays.asList(objectMapper.readValue(json, Card[].class));
            System.out.println("Card: "+cards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(card);

        //card = cardRepository.addCard(cards);
        //System.out.println(card.getId());


        /*for (int i=0;i<=5;i++) {
            card = cardRepository.addCard(card);
        }*/
        //card = cardRepository.addCardToPackage(card,cardPackage.getId());

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization("Basic admin-mtcgToken");
        //response.setAuthorization("Basic "+username+"-mtcgToken");
        //response.setAuthorization("Basic admin-mtcgToken");
        String content = "hi";
        /*try {
            content = objectMapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
        System.out.println(content);
        response.setContent(content);

        return response;
    }
}
