package org.example.application.housing.controller;

import org.example.application.housing.model.House;
import org.example.application.housing.repository.HouseRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;

import java.util.List;

public class HouseController {

    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public Response handle(Request request) {

        if (request.getMethod().equals("GET")) {
            return getHouse(request);
        }

        Response response = new Response();
        response.setStatus(405);
        response.setMessage("Method not allowed");
        response.setContentType("text/plain");
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }

    private Response getHouse(Request request) {
        List<House> houses = this.houseRepository.findAll();

        Response response = new Response();
        response.setStatus(200);
        response.setMessage("OK");
        response.setContentType("text/plain");
        response.setContent(houses.toString());

        return response;
    }
}
