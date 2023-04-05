package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class CountRequest {
    private String targetUserAlias;
    private AuthToken authToken;
    private String type;

    private CountRequest() {}

    public CountRequest(String targetUserAlias, AuthToken authToken, String type) {
        this.targetUserAlias = targetUserAlias;
        this.authToken = authToken;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }

    public void setTargetUserAlias(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
