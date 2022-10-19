package org.iproduct.di.exceptions;

public class ContextInitializationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ContextInitializationException() {
		super();
	}

	public ContextInitializationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ContextInitializationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ContextInitializationException(String arg0) {
		super(arg0);
	}

	public ContextInitializationException(Throwable arg0) {
		super(arg0);
	}

}
