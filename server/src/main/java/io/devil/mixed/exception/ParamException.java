
package io.devil.mixed.exception;

import io.devil.mixed.api.ResponseCode;
import lombok.Getter;

@Getter
public class ParamException extends RuntimeException {

    private final ResponseCode code = ResponseCode.PARAM_ERROR;

    public ParamException() {
        super(ResponseCode.PARAM_ERROR.defaultMsg);
    }

    public ParamException(String msg) {
        super(msg);
    }
}