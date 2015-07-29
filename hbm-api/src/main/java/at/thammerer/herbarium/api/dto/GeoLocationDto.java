package at.thammerer.herbarium.api.dto;


/**
 * @author thammerer
 */
public class GeoLocationDto {

	private Double lat;
	private Double lng;

	public GeoLocationDto() {
	}

	public GeoLocationDto(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
}
