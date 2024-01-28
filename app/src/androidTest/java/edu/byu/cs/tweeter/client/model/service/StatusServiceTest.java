package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleListObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

class StatusServiceTest <T> {

    private User currentUser;
    private StatusService statusServiceSpy;
    private StatusServiceObserver observer;
    private CountDownLatch countDownLatch;

    private class StatusServiceObserver implements SimpleListObserver<T> {

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

    User getDummyUser() {
        return getFakeData().getFirstUser();
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

    @BeforeEach
    public void setup() {
        currentUser = getDummyUser();
        statusServiceSpy = Mockito.spy(new StatusService());
        observer = new StatusServiceObserver();
        resetCountDownLatch();
    }

    @Test
    public void testGetStorySuccess() throws InterruptedException {
        statusServiceSpy.loadMoreItems(currentUser, 3, null, "story", (SimpleListObserver<Status>) observer);
        awaitCountDownLatch();

        Assertions.assertTrue(observer.isSuccess());
        Assertions.assertNull(observer.getMessage());
        Assertions.assertTrue(observer.getHasMorePages());
        Assertions.assertNull(observer.getException());
    }
}

