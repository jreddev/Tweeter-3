package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask {
    private static final String LOG_TAG = "GetFollowingCountTask";
    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken,messageHandler, targetUser);
        type = "following";
    }

    @Override
    public void runTask() {
        try {
            String targetUserAlias = targetUser == null ? null : targetUser.getAlias();

            CountRequest request = new CountRequest(targetUserAlias, authToken, type);
            CountResponse response = getServerFacade().count(request, "/count");

            followeesCount = response.getCount();

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            sendExceptionMessage(e);
            throw new RuntimeException(e.getMessage());
        }

    }
}
