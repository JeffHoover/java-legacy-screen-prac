package com.neopragma.legacy.screen;

import static com.neopragma.legacy.screen.ErrorCode.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import com.neopragma.legacy.screen.integration.PersistanceLayer;

public class JobApplicant {
	
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	
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
		if ( firstName.length() > 0 && lastName.length() > 0 ) {
			return SUCCESS;
		} else {
			return INVALID_NAME;
		}
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
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		boolean done = false;
		Scanner scanner = new Scanner(System.in);
		String firstName = "";
		String middleName = "";
		String lastName = "";
		String ssn = "";
		String zipCode = "";
		while (!done) {
			System.out.println("Please enter info about a job candidate or 'quit' to quit");
			System.out.println("First name?");
            firstName = scanner.nextLine();		
            if (firstName.equals("quit")) {
            	scanner.close();
            	System.out.println("Bye-bye!");
            	done = true;
            	break;
            }
			System.out.println("Middle name?");
            middleName = scanner.nextLine();
			System.out.println("Last name?");
            lastName = scanner.nextLine();			
			System.out.println("SSN?");
            ssn = scanner.nextLine();			
			System.out.println("Zip Code?");
            zipCode = scanner.nextLine();			
            jobApplicant.setName(firstName, middleName, lastName);          
            jobApplicant.setSsn(ssn);
            jobApplicant.lookupCityAndState(zipCode);
            
            PersistanceLayer persistance = new PersistanceLayer();
            persistance.save(jobApplicant);
		}
	}
	
}
