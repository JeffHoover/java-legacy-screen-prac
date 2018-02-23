package com.neopragma.legacy.screen;

import static com.neopragma.legacy.screen.ErrorCode.INVALID_NAME;
import static com.neopragma.legacy.screen.ErrorCode.SSN_BAD_AREA_NAME;
import static com.neopragma.legacy.screen.ErrorCode.SSN_BAD_SERIAL_NUMBER;
import static com.neopragma.legacy.screen.ErrorCode.SSN_REGEX_FAIL;
import static com.neopragma.legacy.screen.ErrorCode.SSN_SPECIAL_CASE;
import static com.neopragma.legacy.screen.ErrorCode.SUCCESS;

import java.io.IOException;
import java.net.URISyntaxException;

public class JobApplicant {
	
	private String firstName;
	private String middleName;
	private String lastName;
	
	private String city;
	private String state;
	
	private String ssn;
	
	private String[] specialCases = new String[] {
	    "219099999", "078051120"
	};
	
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
		} 
		return SUCCESS;
	}

	public void setSsn(String ssn) {
		if ( ssn.matches("(\\d{3}-\\d{2}-\\d{4}|\\d{9})") ) {
  		    this.ssn = ssn.replaceAll("-", "");
		} else {
  		    this.ssn = "";
		}    
	}
	
	public String formatSsn() {
		StringBuilder sb = new StringBuilder(ssn.substring(0,3));
		sb.append("-");
		sb.append(ssn.substring(3,5));
		sb.append("-");
		sb.append(ssn.substring(5));
		return sb.toString();
	}

	public ErrorCode validateSsn() {
		if ( ssnFailsRegex() ) {
			return SSN_REGEX_FAIL;
		}
		if ( ssnHasBadAreaName() ) {
			return SSN_BAD_AREA_NAME;
		}
		if ( ssnHasBadSerialNumber() ) {
			return SSN_BAD_SERIAL_NUMBER;
		}
		if (ssnIsSpecialCase()) {
				return SSN_SPECIAL_CASE;
		}
		return SUCCESS;
	}

	private boolean ssnFailsRegex() {
		return !ssn.matches("\\d{9}");
	}
	
	private boolean ssnIsSpecialCase() {
		for (int i = 0 ; i < specialCases.length ; i++ ) {
			if ( ssn.equals(specialCases[i]) ) {
				return true;
			}
		}
		return false;
	}

	private boolean ssnHasBadSerialNumber() {
		return "0000".equals(ssn.substring(5));
	}

	private boolean ssnHasBadAreaName() {
		return "000".equals(ssn.substring(0,3)) || 
			 "666".equals(ssn.substring(0,3)) ||
			 "9".equals(ssn.substring(0,1));
	}

	public void lookupCityAndState(String zipCode) throws URISyntaxException, IOException {
        
		CityStateLookup lookup = new CityStateLookup();
        
		lookup.lookupCityAndState(zipCode);
        this.city = lookup.getCity();
        this.state = lookup.getState();	
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
		lookupCityAndState(zipCode);
	}
	
}
