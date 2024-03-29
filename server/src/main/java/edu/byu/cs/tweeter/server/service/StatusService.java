package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class StatusService {

    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (request.getStatus() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a status");
        }
        if (request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have an authtoken");
        }
        else
            return new PostStatusResponse(true);
    }
}
