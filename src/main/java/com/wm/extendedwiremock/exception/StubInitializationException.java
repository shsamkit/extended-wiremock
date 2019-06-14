package com.wm.extendedwiremock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StubInitializationException extends RuntimeException {

	private static final Logger logger = LoggerFactory.getLogger(StubInitializationException.class);

	public StubInitializationException(String message) {
		super(message);
		logger.error(message);
	}

	public StubInitializationException(String message, Throwable ex) {
		super(message, ex);
		logger.error(message);
	}

}
