package io.devil.mixed.api;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Brozen
 * @since 2024-02-01
 */
@Getter
@ToString
public class Response<T> {

    /**
     * 业务状态码
     */
    private int code = ResponseCode.SUCCESS.code;

    /**
     * 错误信息，或业务提示信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    private Response() {
    }

    public static Response<Void> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.data = data;
        return response;
    }

    public static Response<Void> fail(ResponseCode code) {
        Response<Void> response = new Response<>();
        response.code = code.code;
        response.msg = code.defaultMsg;
        return response;
    }

    public static Response<Void> fail(ResponseCode code, String msg) {
        Response<Void> response = new Response<>();
        response.code = code.code;
        response.msg = msg;
        return response;
    }

}
