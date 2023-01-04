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

import java.util.ArrayList;
import java.util.List;

public class BattleController {

    List<User> userList = new ArrayList<>();

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

        userList.add(user);

        if(userList.size()>=2){
            User p1 = userList.get(0);
            User p2 = userList.get(1);
            battleRepository.createOrAddUserToBattle(p1);
            Battle battle = battleRepository.createOrAddUserToBattle(p2);
            battleRepository.waitForBattleToFinish(battle);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setContent("battle finish");
        return response;
    }
}
