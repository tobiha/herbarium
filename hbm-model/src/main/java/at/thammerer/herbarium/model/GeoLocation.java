package at.thammerer.herbarium.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author thammerer
 */
@Table(name = "geo_location")
@Entity
public class GeoLocation extends AbstractEntity{

	@NotNull
	@Column(nullable = false, length = 9, precision = 6, columnDefinition = "numeric(9,6)")
	private Double lat;

	@NotNull
	@Column(nullable = false, length = 9, precision = 6, columnDefinition = "numeric(9,6)")
	private Double lng;

	@OneToOne(mappedBy = "location")
	private HerbariumSheet herbariumSheet;

	public GeoLocation() {
	}

	public GeoLocation(Double lat, Double lng) {
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

	public HerbariumSheet getHerbariumSheet() {
		return herbariumSheet;
	}

	public void setHerbariumSheet(HerbariumSheet herbariumSheet) {
		this.herbariumSheet = herbariumSheet;
	}


	public void updateData(GeoLocation updated){
		if(updated == null){
			this.lat = null;
			this.lng = null;
			this.herbariumSheet = null;
			return;
		}

		this.lat = updated.getLat();
		this.lng = updated.getLng();
	}

	public boolean isEmpty() {
		return lat == null && lng == null;
	}
}
