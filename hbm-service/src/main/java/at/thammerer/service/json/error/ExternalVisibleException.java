package at.thammerer.service.json.error;


/**
 * External exceptions which are sent to the caller (external system).
 */
public class ExternalVisibleException extends RuntimeException {

	private ExceptionData exceptionData;

	public ExternalVisibleException(ExceptionData exceptionData) {
		super(exceptionData.getMsg());
		this.exceptionData = exceptionData;
	}

	public ExternalVisibleException(ExceptionData exceptionData, String appendMessage) {
		super(exceptionData.getMsg() + " " + appendMessage);
		this.exceptionData = exceptionData;
	}

	public ExceptionData getExceptionData() {
		return exceptionData;
	}

}
