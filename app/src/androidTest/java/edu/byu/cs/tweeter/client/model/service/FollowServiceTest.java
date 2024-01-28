package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class FollowServiceTest <T> {

    private User currentUser;
    private AuthToken currentAuthToken;
    private FollowService followServiceSpy;
    private FollowServiceObserver observer;
    private CountDownLatch countDownLatch;

    @BeforeEach
    public void setup() {
        currentUser = getDummyUser();
        currentAuthToken = getDummyAuthToken();
        followServiceSpy = Mockito.spy(new FollowService());
        observer = new FollowServiceObserver();
        resetCountDownLatch();
    }
    User getDummyUser() {
        return getFakeData().getFirstUser();
    }
    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }
    FakeData getFakeData() {
        return FakeData.getInstance();
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }
    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    private class FollowServiceObserver implements SimpleListObserver<T> {

        private boolean success;
        private String message;
        private List<T> list;
        private boolean hasMorePages;
        private Exception exception;

        @Override
        public void handleSuccess(List<T> list, boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.list = list;
            this.hasMorePages = hasMorePages;
            this.exception = null;

            countDownLatch.countDown();
        }

        @Override
        public void handleFailure(String message) {
            this.success = false;
            this.message = message;
            this.list = null;
            this.hasMorePages = false;
            this.exception = null;

            countDownLatch.countDown();
        }

        @Override
        public void handleException(Exception exception) {
            this.success = false;
            this.message = null;
            this.list = null;
            this.hasMorePages = false;
            this.exception = exception;

            countDownLatch.countDown();
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<T> getFollowees() {
            return list;
        }

        public boolean getHasMorePages() {
            return hasMorePages;
        }

        public Exception getException() {
            return exception;
        }
    }
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws InterruptedException {
        followServiceSpy.loadMoreItems(currentAuthToken, currentUser, 3, null, "followers", (SimpleListObserver<User>) observer);
        awaitCountDownLatch();

        List<User> expectedFollowees = FakeData.getInstance().getFakeUsers().subList(0, 3);
        Assertions.assertTrue(observer.isSuccess());
        Assertions.assertNull(observer.getMessage());
        Assertions.assertEquals(expectedFollowees, observer.getFollowees());
        Assertions.assertTrue(observer.getHasMorePages());
        Assertions.assertNull(observer.getException());
    }
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws InterruptedException {
        followServiceSpy.loadMoreItems(currentAuthToken, currentUser, 1, null, "followers", (SimpleListObserver<User>) observer);
        awaitCountDownLatch();

        List<T> followees = observer.getFollowees();
        Assertions.assertTrue(followees.size() > 0);
    }
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws InterruptedException {
        followServiceSpy.loadMoreItems(currentAuthToken, null, 0, null, "followers", (SimpleListObserver<User>) observer);
        awaitCountDownLatch();

        Assertions.assertFalse(observer.isSuccess());
        Assertions.assertNull(observer.getMessage());
        Assertions.assertNull(observer.getFollowees());
        Assertions.assertFalse(observer.getHasMorePages());
    }
}