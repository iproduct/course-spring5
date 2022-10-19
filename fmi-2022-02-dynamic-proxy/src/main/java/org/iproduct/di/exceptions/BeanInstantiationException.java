package org.iproduct.di.exceptions;

public class BeanInstantiationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BeanInstantiationException() {
		super();
	}

	public BeanInstantiationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BeanInstantiationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BeanInstantiationException(String arg0) {
		super(arg0);
	}

	public BeanInstantiationException(Throwable arg0) {
		super(arg0);
	}

}
