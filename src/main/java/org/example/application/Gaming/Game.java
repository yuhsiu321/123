package org.example.application.Gaming;

import org.example.application.Gaming.controller.*;
import org.example.application.Gaming.respository.*;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class Game implements Application {

    private UserController userController;
    private CardController cardController;
    private SessionController sessionController;
    private PackageController packageController;
    private TransactionsController transactionsController;


    public Game() {
        UserRepository userRepository = new UserMemoryRepository();
        PackageRepository packageRepository = new PackageMemoryRepository();
        CardRepository cardRepository = new CardMemoryRepository();
        this.packageController = new PackageController(packageRepository,cardRepository);
        this.cardController = new CardController(cardRepository);
        this.userController = new UserController(userRepository);
        this.sessionController = new SessionController(userRepository);
        this.transactionsController = new TransactionsController(packageRepository,cardRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        } else if (request.getPath().startsWith("/packages")) {
            return packageController.handle(request);
        } else if (request.getPath().startsWith("/sessions")) {
            return sessionController.handle(request);
        }else if(request.getPath().startsWith("/transactions/packages")){
            return transactionsController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
