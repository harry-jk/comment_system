package kr.ac.jejunu.harry.exception;

/**
 * Created by jhkang on 2016-06-16.
 */
public class BadRequestException extends RuntimeException {
    private String reason;

    public BadRequestException(String message) {
        this.reason = message;
    }

    public String getReason() {
        return reason;
    }
}
