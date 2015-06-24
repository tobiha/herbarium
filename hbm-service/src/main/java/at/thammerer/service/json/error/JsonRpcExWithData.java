package at.thammerer.service.json.error;

/**
 * @author tobi
 */
public class JsonRpcExWithData extends JsonRpcEx{
	private Object data;

	public JsonRpcExWithData(String message, int errorCode) {
		super(message, errorCode);
	}

	public JsonRpcExWithData(String message, int errorCode, Object data) {
		super(message, errorCode);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
