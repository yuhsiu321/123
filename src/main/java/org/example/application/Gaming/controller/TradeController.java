package org.example.application.Gaming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Trade;
import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.CardRepository;
import org.example.application.Gaming.respository.TradeRepository;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.List;
import java.util.Objects;

public class TradeController {
    private final  CardRepository cardRepository;
    private final TradeRepository tradeRepository;

    private final  UserRepository userRepository;
    Gson gson;
    public TradeController(CardRepository cardRepository, TradeRepository tradeRepository, UserRepository userRepository) {
        gson = new Gson();
        this.cardRepository = cardRepository;
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            if (request.getToken() == null ) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return create(request);
        }else if(request.getMethod().equals(Method.GET.method)){
            if (request.getToken() == null ) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return read(request);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }

    public Response handle2(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            if (request.getToken() == null ) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return trading(request);
        }else if(request.getMethod().equals(Method.DELETE.method)){
            if (request.getToken() == null ) {
                Response response = new Response();
                response.setStatusCode(StatusCode.UNAUTHORIZED);
                response.setContentType(ContentType.TEXT_PLAIN);
                response.setContent(StatusCode.UNAUTHORIZED.message);
                return response;
            }
            return delete(request);
        }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

        return response;
    }
    private Response create(Request request) {

        User user = userRepository.findbyUsername(request.getToken());
        Trade trade = gson.fromJson(request.getContent(),Trade.class);
        trade.setTradeStarter(user.getUsername());

        tradeRepository.addtrade(trade);
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContent("Trade create");

        return response;
    }

    private Response read(Request request) {

        Trade trade = tradeRepository.getTrade(request.getToken());
        if(trade!=null) {
            Response response = new Response();
            response.setStatusCode(StatusCode.OK);
            response.setContent(gson.toJson(trade));
            return response;

        }else {
            Response response = new Response();
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setContent("This user did not have a deal");
            return response;
        }
    }

    private Response delete(Request request){
        int index = request.getPath().indexOf("/");
        index = request.getPath().indexOf("/",index+1);
        String notfinishtradeid = request.getPath().substring(index);
        String tradeid = notfinishtradeid.replace("/", "");

        tradeRepository.deleteTrade(tradeid);
        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContent("delete　successfully");
        return response;
    }

    private Response trading(Request request){
        int index = request.getPath().indexOf("/");
        index = request.getPath().indexOf("/",index+1);
        String notfinishtradeid = request.getPath().substring(index);
        String tradeid = notfinishtradeid.replace("/", "");
        Trade trade  = tradeRepository.getTradebyId(tradeid);
        if(Objects.equals(trade.getTradeStarter(), request.getToken())){
            Response response = new Response();
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setContent("Can not trade with yourself");
            return response;
        }
        Card card1 = cardRepository.getCard(trade.getCardToTrade());
        String card2id = gson.fromJson(request.getContent(), String.class);
        Card card = cardRepository.getCard(card2id);
        if((card.getCardType().toLowerCase()).equals(trade.getType())&&card.getDamage()>=trade.getMinimumDamage()) {
            tradeRepository.trade(card.getUser_id(),trade.getCardToTrade());
            tradeRepository.trade(card1.getUser_id(),card2id);
            tradeRepository.deleteTrade(tradeid);
            Response response = new Response();
            response.setStatusCode(StatusCode.OK);
            response.setContent("trade successfully");
            return response;
        }else {
            Response response = new Response();
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setContent("Card type or Damage not match");
            return response;
        }

    }
}
