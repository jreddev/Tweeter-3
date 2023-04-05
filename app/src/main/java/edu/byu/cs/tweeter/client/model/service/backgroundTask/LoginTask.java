package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {

    private static final String LOG_TAG = "LoginTask";

    public LoginTask(String username, String password, Handler messageHandler) {
        super(username,password,messageHandler);
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() {
        /*//TODO:: REMOVE OLD PART
        User user = getFakeData().getFirstUser();
        AuthToken auth = getFakeData().getAuthToken();
        return new Pair<>(user,auth);*/

       try {
            LoginRequest request = new LoginRequest(username, password);
            LoginResponse response = getServerFacade().login(request, "/login");

            User loggedInUser = response.getUser();
            AuthToken authToken = response.getAuthToken();
            return new Pair<>(loggedInUser, authToken);

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            sendExceptionMessage(e);
            throw new RuntimeException(e.getMessage());
        }

    }

}
