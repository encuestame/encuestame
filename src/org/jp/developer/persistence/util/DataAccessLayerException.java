package org.jp.developer.persistence.util;

public class DataAccessLayerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessLayerException() {
	}

	public DataAccessLayerException(String message) {
		super(message);
		System.out.println("Exception HB"+ message);
	}

	public DataAccessLayerException(Throwable cause) {
		super(cause);
	}

	public DataAccessLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
