package com.mongodash.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mongodash.exception.HttpRequestHandlerException;

@ControllerAdvice
public class ExceptionControllerAdvice {

	
	// ~ Exceptions handlers ----------------------------------------------------------
	// --------------------------------------------------------------------------------
	
	/* 
	 * Esse advice executa antes do postHandle dos interceptors e portanto ainda não tem 
	 * o BASE_URL no model... por isso temos que usar forward ou redirect aqui.. 
	 */
	
	//TODO - remover
	@ExceptionHandler(HttpRequestHandlerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleHttpRequestHandlerException(HttpRequestHandlerException exception) {
		return "forward:/500";
	}
}