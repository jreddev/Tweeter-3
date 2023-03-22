package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class GetCountTask extends AuthenticatedTask {
    //public static final int COUNT = 20;
    protected int followersCount;
    protected int followeesCount;
    protected String type;
    /**
     * The user whose following count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected final User targetUser;

    protected GetCountTask(AuthToken authToken, Handler messageHandler, User targetUser) {
        super(authToken, messageHandler);
        this.targetUser = targetUser;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        if (type.equals("followers"))
            msgBundle.putInt(COUNT_KEY, followersCount);
        else
            msgBundle.putInt(COUNT_KEY, followeesCount);
    }
}
