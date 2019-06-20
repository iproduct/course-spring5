package bookstore.exception;

public class EntityExistsException extends Exception {

	public EntityExistsException() {
	}

	public EntityExistsException(String arg0) {
		super(arg0);
	}

	public EntityExistsException(Throwable arg0) {
		super(arg0);
	}

	public EntityExistsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EntityExistsException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
