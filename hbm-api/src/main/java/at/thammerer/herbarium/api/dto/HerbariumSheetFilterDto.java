package at.thammerer.herbarium.api.dto;

import java.util.Date;

/**
 * @author thammerer
 */
public class HerbariumSheetFilterDto {

	private Long number;

	private String scientificName;

	private String family;

	private String subSpecies;

	private Date collectionDateFrom;

	private Date collectionDateTo;

	private String collector;

	private String locationDescription;

	private String altitude;

	private String exposition;

	private String habitatInformation;

	private String annotations;

	public HerbariumSheetFilterDto() {
	}


	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getSubSpecies() {
		return subSpecies;
	}

	public void setSubSpecies(String subSpecies) {
		this.subSpecies = subSpecies;
	}

	public Date getCollectionDateFrom() {
		return collectionDateFrom;
	}

	public void setCollectionDateFrom(Date collectionDateFrom) {
		this.collectionDateFrom = collectionDateFrom;
	}

	public Date getCollectionDateTo() {
		return collectionDateTo;
	}

	public void setCollectionDateTo(Date collectionDateTo) {
		this.collectionDateTo = collectionDateTo;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getExposition() {
		return exposition;
	}

	public void setExposition(String exposition) {
		this.exposition = exposition;
	}

	public String getHabitatInformation() {
		return habitatInformation;
	}

	public void setHabitatInformation(String habitatInformation) {
		this.habitatInformation = habitatInformation;
	}

	public String getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}
}
