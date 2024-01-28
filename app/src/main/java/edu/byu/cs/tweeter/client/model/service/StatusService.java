package edu.byu.cs.tweeter.client.model.service;

import java.util.Objects;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleListHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleListObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends Service {
    public void postStatusTask(Status newStatus, SimpleNotificationObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new SimpleNotificationHandler(observer));
        ExecuteTask(statusTask);
    }

    public void loadMoreItems(User user, int pageSize, Status lastStatus, String type, SimpleListObserver<Status> observer) {
        if (Objects.equals(type, "story")){
            GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                    user, pageSize, lastStatus, new SimpleListHandler<Status>(observer, "story"));
            ExecuteTask(getStoryTask);
        }
        else if (Objects.equals(type, "feed")){
            GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                    user, pageSize, lastStatus, new SimpleListHandler<Status>(observer, "feed"));
            ExecuteTask(getFeedTask);
        }
        else{
            throw new RuntimeException("Wrong input: feed or story in FollowService");
        }
    }


}
