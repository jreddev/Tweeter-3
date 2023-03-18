package edu.byu.cs.tweeter.client.presenter;

public class GetLoginPresenter extends AuthPresenter {

    public GetLoginPresenter(AuthView view, String type) {
        super(view, type);
    }

    public void login(String alias, String password) {
        userService.login(alias,password, new GetAuthObserver());
    }
}
