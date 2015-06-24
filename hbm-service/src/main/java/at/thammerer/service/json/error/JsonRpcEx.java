package at.thammerer.service.json.error;

/**
 * User: Eduard Szente Date: 7/1/12 Time: 9:45 PM
 */
public class JsonRpcEx extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errorCode;

	public JsonRpcEx(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
