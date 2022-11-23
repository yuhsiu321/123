package org.example.application.Gaming;

import org.example.application.Gaming.controller.PackageController;
import org.example.application.Gaming.controller.UserController;
import org.example.application.Gaming.respository.PackageMemoryRepository;
import org.example.application.Gaming.respository.PackageRepository;
import org.example.application.Gaming.respository.UserMemoryRepository;
import org.example.application.Gaming.respository.UserRepository;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class Game implements Application {

    private UserController userController;
    private PackageController packageController;


    public Game() {
        UserRepository userRepository = new UserMemoryRepository();
        PackageRepository packageRepository = new PackageMemoryRepository();
        this.packageController = new PackageController(packageRepository);
        this.userController = new UserController(userRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        } else if (request.getPath().startsWith("/package")) {
            return packageController.handle(request);
        } else if (request.getPath().startsWith("/sessions")) {
            return userController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}