package org.example.server.util;

import org.example.application.Gaming.model.User;
import org.example.application.Gaming.respository.UserMemoryRepository;
import org.example.server.dto.Request;
import org.example.server.exception.UnsupportedProtocolException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestBuilder {

    static String authUser;

    /**
     * Takes an HTTP request as a string and builds and
     * returns a Request object.
     *
     * @param requestString HTTP request as a string
     * @return HTTP request as an object
     */
    public static Request build(String requestString) throws UnsupportedProtocolException {
        Request request = new Request();
        request.setRequest(requestString);

        // Method
        request.setMethod(getMethod(requestString));

        // Path
        request.setPath(getPath(requestString));

        // Content Length
        request.setContentLength(HttpRegex.findHeaderAsInt(requestString, "Content-Length"));

        // Content
        request.setContent(HttpRegex.findContent(requestString));

        //request.setAuthorization(getAuthorization(requestString));
        //request.setAuthorization(HttpRegex.findHeaderAsString(requestString, "Authorization"));

        request.setToken(authorizeRequest(requestString,"Authorization"));
        // TODO: Add additional information to the request

        return request;
    }

    private static String getMethod(String requestString) throws UnsupportedProtocolException {
        String method = HttpRegex.findMethod(requestString);

        if (null == method) {
            throw new UnsupportedProtocolException("No HTTP method in request");
        }

        return method;
    }

    private static String getPath(String requestString) throws UnsupportedProtocolException {
        String path = HttpRegex.findPath(requestString);

        if (null == path) {
            throw new UnsupportedProtocolException("No HTTP path in request");
        }

        return path;
    }

    private static String authorizeRequest(String requestString, String header) {
        String value = findHeader(requestString,header);
        if (value != null) {
            String token = value.replace(" Basic ", "");
            String[] parts = token.split("-");
            if (parts.length == 2) {
                User user = UserMemoryRepository.getInstance().findbyUsername(parts[0]);
                if (user != null && token.equals(user.getToken())) {
                    authUser = user.getUsername();
                    return authUser;
                }
            }
        }
        return null;
    }

    private static String findHeader(String request, String header) {
        Pattern r = Pattern.compile("^" + header + ":\\s(.+)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(request);

        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }
}
