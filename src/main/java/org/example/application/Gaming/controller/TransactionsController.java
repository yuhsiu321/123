package org.example.application.Gaming.controller;

import com.google.gson.Gson;
import org.example.application.Gaming.model.Package;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.CardRepository;
import org.example.application.Gaming.respository.PackageRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class TransactionsController {
    private final PackageRepository packageRepository;
    private final CardRepository cardRepository;
    Gson gson;

    public TransactionsController(PackageRepository packageRepository,CardRepository cardRepository){
        gson = new Gson();
        this.packageRepository = packageRepository;
        this.cardRepository = cardRepository;
    }

    public Response handle(Request request){
        if(request.getMethod().equals(Method.POST.method)){
            if(request.getToken() == null){
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return acquire(request);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);
        return response;
    }

    private Response acquire(Request request){

        User user = new User();
        user.setUsername(request.getToken());
        Package cardPackage = packageRepository.getRandomPackage();
        if (cardPackage != null && packageRepository.addPackageToUser(cardPackage,user)){
            Response response = new Response();
            response.setStatusCode(StatusCode.CREATED);
            response.setContentType(ContentType.APPLICATION_JSON);
            response.setContent("cards acquire successfully");
            return response;
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent("cards acquire false");
        return response;
    }
}
