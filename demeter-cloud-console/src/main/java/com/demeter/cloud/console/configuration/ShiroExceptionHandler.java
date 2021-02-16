package com.demeter.cloud.console.configuration;

import com.demeter.cloud.core.util.ResponseUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public Object unauthenticatedHandler(AuthenticationException exception) {
		exception.printStackTrace();
		return ResponseUtil.unlogin();
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public Object unauthorizedHandler(AuthorizationException exception) {
		exception.printStackTrace();
		return ResponseUtil.unauthz();
	}

}
