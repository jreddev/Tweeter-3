package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest {
    public AuthToken authToken;
    private LogoutRequest() {}
    public LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
