package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthenticatedTask {
    private static final String LOG_TAG = "GetUserTask";

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private final String alias;
    private User user;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken,messageHandler);
        this.alias = alias;
    }

    @Override
    protected void runTask() throws IOException {
        user = getUser();
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
    }

    private User getUser() {
        //User user = getFakeData().findUserByAlias(alias);
        try {
            GetUserRequest request = new GetUserRequest(authToken, alias);
            GetUserResponse response = getServerFacade().getUser(request, "/getuser");

            user = response.getUser();
            return user;
        } catch (IOException | TweeterRemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
