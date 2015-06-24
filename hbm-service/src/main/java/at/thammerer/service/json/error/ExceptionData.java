package at.thammerer.service.json.error;

/**
 * This class contains all error code constant.
 *
 * @author Eduard Szente
 */
public class ExceptionData {

	public static ExceptionData UNEXPECTED = new ExceptionData(-1, "Unexpected error, please see logfile!");

	public ExceptionData(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private final int code;
	private final String msg;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
