package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author Kevin Tran
 * @version 2020-04-08
 * The persistent class for the PropertyAssessment database table.
 * 
 */
@Entity
@NamedQuery(name="PropertyAssessment.findAll", query="SELECT p FROM PropertyAssessment p")
public class PropertyAssessment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String accountNumber;

	private long assessedValue;

	private String assessmentClass;

	private String garage;

	private String houseNumber;

	private double latitude;

	private double longitude;

	private String neighbourhood;

	private int neighbourhoodId;

	private String streetName;

	private String suite;

	private int version;

	private String ward;

	public PropertyAssessment() {
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getAssessedValue() {
		return this.assessedValue;
	}

	public void setAssessedValue(long assessedValue) {
		this.assessedValue = assessedValue;
	}

	public String getAssessmentClass() {
		return this.assessmentClass;
	}

	public void setAssessmentClass(String assessmentClass) {
		this.assessmentClass = assessmentClass;
	}

	public String getGarage() {
		return this.garage;
	}

	public void setGarage(String garage) {
		this.garage = garage;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getNeighbourhood() {
		return this.neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public int getNeighbourhoodId() {
		return this.neighbourhoodId;
	}

	public void setNeighbourhoodId(int neighbourhoodId) {
		this.neighbourhoodId = neighbourhoodId;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getSuite() {
		return this.suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getWard() {
		return this.ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

}