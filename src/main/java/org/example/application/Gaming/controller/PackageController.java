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
            if (request.getToken() == null || !"admin".equalsIgnoreCase(request.getToken())) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return create(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response create(Request request) {
        Gson gson = new Gson();
        Package cardPackage = packageRepository.addPackage();
        Card[] cards = gson.fromJson(request.getContent(),Card[].class);
        for(Card card:cards){
            card = cardRepository.addCard(card);
            card = cardRepository.addCardToPackage(card,cardPackage.getId());
            //System.out.println(card);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent("package create");
        return response;
    }

}
