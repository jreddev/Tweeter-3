package edu.byu.cs.tweeter.client.model.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.CountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

class ServerFacadeTest {
    public ServerFacade serverFacade;
    public AuthToken token;

    @BeforeEach
    void setUp() {
        serverFacade = new ServerFacade();
        token = new AuthToken();
        token.setToken("token");
    }

    @Test
    void register() throws IOException, TweeterRemoteException {
        RegisterRequest request = new RegisterRequest("Jared", "Devenport", "jareddev", "password", "https://students.cs.byu.edu/~jareddev/cs340/icons/boy.png");
        LoginResponse response =  serverFacade.register(request, "/register");
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getAuthToken());
        Assertions.assertNotNull(response.getUser());
    }

    @Test
    void getFollowers() throws IOException, TweeterRemoteException {
        FollowersRequest request = new FollowersRequest(token, "alias", 10, null);
        FollowersResponse response = serverFacade.getFollowers(request, "/getfollowers");
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getFollowers());
    }

    @Test
    void getFollowersCount() throws IOException, TweeterRemoteException {
        CountRequest request = new CountRequest("targetUserAlias", token, "followers");
        CountResponse response = serverFacade.count(request, "/count");
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getCount());
    }
}