package at.thammerer.herbarium.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author thammerer
 */
@Entity
@Table(name = "herbarium_sheet")
public class HerbariumSheet extends AbstractEntity{

	@Column(unique = true, nullable = false)
	@NotNull
	private Long number;

	@NotNull
	@Column(name = "scientific_name", nullable = false)
	private String scientificName;

	@Column
	private String family;

	@Column(name = "sub_species")
	private String subSpecies;

	@Column(name = "collection_date")
	@Temporal(TemporalType.DATE)
	private Date collectionDate;

	@Column
	private String collector;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="geo_location_id")
	private GeoLocation location;

	@Column(name = "location_description")
	private String locationDescription;

	@Column
	private String altitude;

	@Column
	private String exposition;

	@Column(name = "habitat_information")
	private String habitatInformation;

	@Column
	private String annotations;

	@Column(name = "storage_location")
	private String storageLocation;


	public HerbariumSheet() {
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

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
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


	@Override
	public String toString() {
		return "HerbariumSheet{" +
			"number=" + number +
			", scientificName='" + scientificName + '\'' +
			", family='" + family + '\'' +
			", subSpecies='" + subSpecies + '\'' +
			", collectionDate=" + collectionDate +
			", collector='" + collector + '\'' +
			", location=" + location +
			", locationDescription='" + locationDescription + '\'' +
			", altitude='" + altitude + '\'' +
			", exposition='" + exposition + '\'' +
			", habitatInformation='" + habitatInformation + '\'' +
			", annotations='" + annotations + '\'' +
			", storageLocation='" + storageLocation + '\'' +
			'}';
	}

	public void updateData(HerbariumSheet updated){
		this.scientificName = updated.getScientificName();
		this.family = updated.getFamily();
		this.subSpecies = updated.getSubSpecies();
		this.collectionDate = updated.getCollectionDate();
		this.collector = updated.getCollector();
		this.locationDescription = updated.getLocationDescription();
		this.altitude = updated.getAltitude();
		this.exposition = updated.getExposition();
		this.habitatInformation = updated.getHabitatInformation();
		this.annotations = updated.getAnnotations();
		this.storageLocation = updated.getStorageLocation();
	}


}
