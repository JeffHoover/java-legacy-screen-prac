package com.neopragma.legacy.screen;

import static com.neopragma.legacy.ErrorCode.INVALID_NAME;
import static com.neopragma.legacy.ErrorCode.SUCCESS;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import com.neopragma.legacy.CityStateLookup;
import com.neopragma.legacy.ErrorCode;
import com.neopragma.legacy.integration.PersistenceLayer;

public class JobApplicant {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String city;
	private String state;
	private String ssn;
	
	SsnValidator ssnValidator = new SsnValidator();
	
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
	
	public String formattedSsn() {
		return ssnValidator.formatSsn(ssn);
	}

	public ErrorCode validateSsn() {
		return ssnValidator.validate(ssn);
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
            jobApplicant.lookupCityState(zipCode);
            PersistenceLayer persistanceLayer = new PersistenceLayer();
            persistanceLayer.save(jobApplicant);
		}
	}
}
