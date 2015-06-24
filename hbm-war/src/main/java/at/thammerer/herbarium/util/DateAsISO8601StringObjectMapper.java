package at.thammerer.herbarium.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.TimeZone;

/**
 * @author thammerer
 */
public class DateAsISO8601StringObjectMapper extends ObjectMapper {

	public DateAsISO8601StringObjectMapper() {
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		setTimeZone(TimeZone.getDefault());
	}
}
