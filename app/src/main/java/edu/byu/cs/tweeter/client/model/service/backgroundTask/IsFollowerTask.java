package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowingResponse;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthenticatedTask {
    private static final String LOG_TAG = "IsFollowerTask";
    /**
     * The alleged follower.
     */
    private User follower;
    /**
     * The alleged followee.
     */
    private User followee;
    private boolean isFollower;

    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.follower = follower;
        this.followee = followee;
    }
    @Override
    protected void runTask() {
        try {
            String followerAlias = follower == null ? null : follower.getAlias();
            String followeeAlias = followee == null ? null : followee.getAlias();

            IsFollowingRequest request = new IsFollowingRequest(authToken, followerAlias, followeeAlias);
            IsFollowingResponse response = getServerFacade().isFollowing(request, "/isfollowing");

            isFollower = response.getIsFollowing();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TweeterRemoteException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }
}
