package org.iproduct.di.exceptions;

public class BeanLookupException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BeanLookupException() {
		super();
	}

	public BeanLookupException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BeanLookupException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BeanLookupException(String arg0) {
		super(arg0);
	}

	public BeanLookupException(Throwable arg0) {
		super(arg0);
	}

}
