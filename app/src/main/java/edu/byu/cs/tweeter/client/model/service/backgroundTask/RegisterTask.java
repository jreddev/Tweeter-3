package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {
    private static final String LOG_TAG = "RegisterTask";
    /**
     * The user's first name.
     */
    private final String firstName;
    /**
     * The user's last name.
     */
    private final String lastName;
    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private final String image;

    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(username,password,messageHandler);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() {
        /*User registeredUser = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();
        return new Pair<>(registeredUser, authToken);*/
        try {
            RegisterRequest request = new RegisterRequest(firstName, lastName, username, password, image);
            LoginResponse response = getServerFacade().register(request, "/register");

            User loggedInUser = response.getUser();
            AuthToken authToken = response.getAuthToken();
            return new Pair<>(loggedInUser, authToken);

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            sendExceptionMessage(e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
