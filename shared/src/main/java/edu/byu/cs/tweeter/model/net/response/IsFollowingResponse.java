package edu.byu.cs.tweeter.model.net.response;

public class IsFollowingResponse extends Response {
    private boolean isFollowing;
    public IsFollowingResponse(String message) {super(false, message);}

    public IsFollowingResponse(boolean isFollowing) {
        super(true);
        this.isFollowing = isFollowing;
    }

    public boolean getIsFollowing(){return isFollowing;}
}
