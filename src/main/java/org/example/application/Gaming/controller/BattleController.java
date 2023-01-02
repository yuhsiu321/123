package org.example.application.Gaming.controller;

import com.google.gson.Gson;
import org.example.application.Gaming.model.Battle;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.BattleRepository;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class BattleController {
    private final BattleRepository battleRepository;
    private final UserRepository userRepository;
    Gson gson;

    public BattleController(BattleRepository battleRepository,UserRepository userRepository) {
        gson = new Gson();
        this.battleRepository = battleRepository;
        this.userRepository = userRepository;
    }
    public Response handle(Request request){
        if(request.getMethod().equals(Method.POST.method)){
            if (request.getToken() == null ) {
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

    private Response create(Request request){
        User user ;
        user = userRepository.findbyUsername(request.getToken());

        Battle battle = (Battle) battleRepository.createOrAddUserToBattle(user);
        Battle battleResult = (Battle) battleRepository.waitForBattleToFinish(battle);

        if(battleResult!=null){
            Response response = new Response();
            response.setStatusCode(StatusCode.OK);
            response.setContentType(ContentType.APPLICATION_JSON);
            response.setContent(gson.toJson(battleResult));
            return response;
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.BAD_REQUEST);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.BAD_REQUEST.message);
        return response;
    }
}
