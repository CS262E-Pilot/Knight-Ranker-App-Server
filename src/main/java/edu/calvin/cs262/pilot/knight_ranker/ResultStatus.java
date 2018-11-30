package edu.calvin.cs262.pilot.knight_ranker;

public class ResultStatus {
    private String message;

    public ResultStatus() {

    }

    public ResultStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
