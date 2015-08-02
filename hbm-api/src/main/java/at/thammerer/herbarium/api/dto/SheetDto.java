package at.thammerer.herbarium.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @author thammerer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SheetDto {

	private Long id;

	private Long number;

	private String scientificName;

	private String family;

//	delete
	private String taxon;

	private String subSpecies;

	private Date collectionDate;

	private String collector;

	private GeoLocationDto location;

	private String locationDescription;

	private String altitude;

	private String exposition;

	private String habitatInformation;

	private String annotations;

	private String storageLocation;

	public SheetDto() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTaxon() {
		return taxon;
	}

	public void setTaxon(String taxon) {
		this.taxon = taxon;
	}

	public String getSubSpecies() {
		return subSpecies;
	}

	public void setSubSpecies(String subSpecies) {
		this.subSpecies = subSpecies;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	public GeoLocationDto getLocation() {
		return location;
	}

	public void setLocation(GeoLocationDto location) {
		this.location = location;
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

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
}
