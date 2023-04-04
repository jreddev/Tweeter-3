package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowingRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
        return getFollowingDAO().getFollowees(request);
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
        return getFollowingDAO().getFollowers(request);
    }

    public GetFeedResponse getFeed(GetFeedRequest request) {
        if(request.getTargetUser() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a target user");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have an authtoken");
        }
        return getFollowingDAO().getFeed(request);
    }

    public FollowResponse follow(FollowRequest request) {
        if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a followee alias");
        }
        return getFollowingDAO().follow(request);
    }
    public IsFollowingResponse isFollowing(IsFollowingRequest request) {
        if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a followee alias");
        }
        if (request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a followee alias");
        }
        return getFollowingDAO().isFollowing(request);
    }

    public CountResponse count(CountRequest request) {
        if (request.getTargetUserAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a target user alias");
        }
        if (request.getType() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a type");
        }
        if (request.getType().equals("followers"))
            return new CountResponse(getFollowingDAO().getFollowerCount(request.getTargetUserAlias()));
        else
            return new CountResponse(getFollowingDAO().getFolloweeCount(request.getTargetUserAlias()));

    }


    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowDAO getFollowingDAO() {
        return new FollowDAO();
    }

}
