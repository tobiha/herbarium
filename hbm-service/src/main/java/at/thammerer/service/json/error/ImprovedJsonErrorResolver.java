package at.thammerer.service.json.error;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.DefaultErrorResolver;
import com.googlecode.jsonrpc4j.ErrorResolver;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:eduard.szente@gmx.at">Eduard Szente</a>
 */
public class ImprovedJsonErrorResolver implements ErrorResolver {

	private static final Logger LOG = LoggerFactory.getLogger(ImprovedJsonErrorResolver.class);

	private int defaultErrorCode;
	private Class defaultExceptionType;
	private String defaultErrorMsgPrefix = "General Error (see log for details), Message: ";
	private Set<Class> clazzesNotToLogStacktrace = new HashSet<Class>(0);

	public ImprovedJsonErrorResolver(int defaultErrorCode, Class defaultExceptionType) {
		this.defaultErrorCode = defaultErrorCode;
		this.defaultExceptionType = defaultExceptionType;
	}

	public String getDefaultErrorMsgPrefix() {
		return defaultErrorMsgPrefix;
	}

	public void setDefaultErrorMsgPrefix(String defaultErrorMsgPrefix) {
		this.defaultErrorMsgPrefix = defaultErrorMsgPrefix;
	}

	public Set<Class> getClazzesNotToLogStacktrace() {
		return clazzesNotToLogStacktrace;
	}

	public void setClazzesNotToLogStacktrace(Set<Class> clazzesNotToLogStacktrace) {
		this.clazzesNotToLogStacktrace = clazzesNotToLogStacktrace;
	}

	@Override
	public JsonError resolveError(Throwable t, Method method, List<JsonNode> jsonNodes) {
		JsonError err;
		if (t instanceof JsonRpcExWithData) {
			JsonRpcExWithData ex = (JsonRpcExWithData) t;
			err = new JsonError(ex.getErrorCode(), ex.getMessage(), ex.getData());
			return err;
		} else if (t instanceof JsonRpcEx) {
			JsonRpcEx ex = (JsonRpcEx) t;
			err = new JsonError(ex.getErrorCode(), ex.getMessage(), new DefaultErrorResolver.ErrorData(ex.getClass().getName(), ex.getMessage()));
		} else if (t instanceof JsonRpcClientException) {
			JsonRpcClientException ex = (JsonRpcClientException) t;
			err = new JsonError(ex.getCode(), ex.getMessage(), ex.getData());
		} else if (t instanceof ExternalVisibleException) {
			ExternalVisibleException ex = (ExternalVisibleException) t;
			err = new JsonError(ex.getExceptionData().getCode(), ex.getMessage(), new DefaultErrorResolver.ErrorData(ex.getClass().getName(), ex.getMessage()));
		} else {
			err = new JsonError(defaultErrorCode, defaultErrorMsgPrefix + t.getMessage(),
				new DefaultErrorResolver.ErrorData(defaultExceptionType.getName(), defaultErrorMsgPrefix + t.getMessage()));
		}

		if (clazzesNotToLogStacktrace.contains(t.getClass())) {
			LOG.info("resolved error: " + t.getMessage());
		} else {
			LOG.error(t.getMessage(), t);
		}

		return err;
	}

}
