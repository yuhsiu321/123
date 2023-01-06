package org.example.application.Gaming.controller;

import com.google.gson.Gson;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class StatController {
    private final UserRepository userRepository;
    Gson gson;

    public StatController(UserRepository userRepository) {
        gson = new Gson();
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

    private Response readAll(Request request){
        User user;
        user= userRepository.findStatbyUsername(request.getToken());
        String returnBody;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(user.getUsername());
        stringBuilder
                .append("( Elo : ")
                .append(user.getElo())
                .append(", Total Battles: ")
                .append(user.getTotalBattle())
                .append(", Win Battles: ")
                .append(user.getWinBattlles())
                .append(", Lost Battles: ")
                .append(user.getLostBattles())
                .append(")");

        returnBody = stringBuilder.toString();
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(returnBody);
        return response;
    }
}
