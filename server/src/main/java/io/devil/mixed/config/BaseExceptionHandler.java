package io.devil.mixed.config;//package com.tzgo.afo.server.config;

import io.devil.mixed.api.Response;
import io.devil.mixed.api.ResponseCode;
import io.devil.mixed.exception.ParamException;
import io.devil.mixed.exception.SessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常信息统一处理类
 *
 * @author Devil
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler(value = { ParamException.class })
	public Response<Void> handParam(ParamException e) {
		log.warn("校验异常 {}", e.getMessage());
		return Response.fail(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(value = { SessionException.class })
	public Response<Void> handSession(SessionException e) {
		return Response.fail(e.getCode());
	}

	@ExceptionHandler(value = { Throwable.class })
	public Response<Void> unknownException(Throwable e) {
		log.error("系统异常: {}", e);
		return Response.fail(ResponseCode.SYSTEM_ERROR, e.getMessage());
	}

}
