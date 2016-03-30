package com.fbposterflex.api.exception;

import java.io.Serializable;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BusinessException extends RuntimeException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8853273999950326870L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException() {

	}

}
