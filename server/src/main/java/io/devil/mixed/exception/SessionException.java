package io.devil.mixed.exception;


import io.devil.mixed.api.ResponseCode;
import lombok.Getter;

@Getter
public class SessionException extends RuntimeException {

    private final ResponseCode code = ResponseCode.UNAUTHORIZED;

    public SessionException() {
        super(ResponseCode.UNAUTHORIZED.defaultMsg);
    }

}
