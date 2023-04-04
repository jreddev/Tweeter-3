package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {
    private static final String LOG_TAG = "GetFeedTask";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken,messageHandler, targetUser, limit, lastStatus);

    }

    protected Pair<List<Status>, Boolean> getItems() {
        //return getFakeData().getPageOfStatus(getLastItem(), getLimit());
        try {
            GetFeedRequest request = new GetFeedRequest(authToken, targetUser, limit, lastItem);
            GetFeedResponse response = getServerFacade().getFeed(request, "/getfeed");

            if (response.isSuccess()) {
                this.items = response.getStatuses();
                this.hasMorePages = response.getHasMorePages();
                sendSuccessMessage();
                return new Pair<>(items, hasMorePages);
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get followers", ex);
            sendExceptionMessage(ex);
        }
        return null; //should never reach this!
    }

}
