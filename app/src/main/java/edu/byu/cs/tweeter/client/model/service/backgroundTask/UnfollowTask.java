package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthenticatedTask {
    private static final String LOG_TAG = "UnfollowTask";

    /**
     * The user that is being followed.
     */
    private User followee;

    public UnfollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(authToken,messageHandler);
        this.followee = followee;
    }

    @Override
    public void runTask() throws IOException {
        try {
            String followeeAlias = followee == null ? null : followee.getAlias();

            FollowRequest request = new FollowRequest(authToken, followeeAlias);
            FollowResponse response = getServerFacade().follow(request, "/unfollow");

            //TODO:: This should remove someone from the following table...

            if (response.isSuccess()) {
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (TweeterRemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
