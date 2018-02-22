package com.neopragma.legacy.screen;

import static com.neopragma.legacy.screen.ErrorCode.INVALID_NAME;
import static com.neopragma.legacy.screen.ErrorCode.SUCCESS;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class JobApplicant {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String city;
	private String state;
	private String ssn;
	
	SsnUtilities ssnUtilities = new SsnUtilities();
	
	public void setName(String firstName, String middleName, String lastName) {
		this.firstName = firstName == null ? "" : firstName;
		this.middleName = middleName == null ? "" : middleName;
		this.lastName = lastName == null ? "" : lastName;
	}
	
	public void setSpanishName(String primerNombre, String segundoNombre,
							   String primerApellido, String segundoApellido) {
		this.firstName = primerNombre == null ? "" : primerNombre;
		this.middleName = segundoNombre == null ? "" : segundoNombre;
		if ( primerApellido != null ) {
  		    StringBuilder sb = new StringBuilder(primerApellido);
		    sb.append(segundoApellido == null ? null : " " + segundoApellido);
		    this.lastName = sb.toString();
		} else {
			this.lastName = "";
		}
	}
	
	public String formatLastNameFirst() {
		StringBuilder sb = new StringBuilder(lastName);
		sb.append(", ");
		sb.append(firstName);
		if ( middleName.length() > 0 ) {
			sb.append(" ");
			sb.append(middleName);
		}
		return sb.toString();
	}
	
	public ErrorCode validateName() {
		if ( firstName.isEmpty() || lastName.isEmpty() ) {
			return INVALID_NAME;
		} else {
			return SUCCESS;
		}
	}

	public void setSsn(String ssn) {
		this.ssn = ssnUtilities.handleDashes(ssn);
	}
	
	public String formattedSsn() {
		return ssnUtilities.formatSsn(ssn);
	}

	public ErrorCode validateSsn() {
		return ssnUtilities.validate(ssn);
	}

	public void lookupCityState(String zipCode) throws URISyntaxException, IOException {
		CityStateLookup cityStateLookup = new CityStateLookup();
		cityStateLookup.lookup(zipCode);
		city = cityStateLookup.getCity();
		state = cityStateLookup.getState();
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}
	
	public void add(String firstName,
			       String middleName,
			       String lastName,
			       String ssn,
			       String zipCode) throws URISyntaxException, IOException {
		setName(firstName, middleName, lastName);
		setSsn(ssn);
		lookupCityState(zipCode);
	}
	
}
