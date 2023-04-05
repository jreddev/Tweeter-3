package edu.byu.cs.tweeter.model.net.response;

public class CountResponse extends Response {
    private Integer count;
    public CountResponse(String message){super(false, message);}

    public CountResponse(Integer count) {
        super(true, null);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
