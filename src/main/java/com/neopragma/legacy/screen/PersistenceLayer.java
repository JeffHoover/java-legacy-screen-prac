package com.neopragma.legacy.screen;

import java.io.IOException;
import java.net.URISyntaxException;

/*
 * A dummy generic persistence layer for the purpose of the kata.
 */
public class PersistenceLayer {
	
	public void save(JobApplicant jobApplicant) {
		System.out.println("Saving to database: " + jobApplicant.formatLastNameFirst());
	}

	public JobApplicant get(String zip) throws URISyntaxException, IOException {
		return makeDummyApplicant(zip);
	}

	private JobApplicant makeDummyApplicant(String zipCode) throws URISyntaxException, IOException {
		JobApplicant dummyApplicant = new JobApplicant();
		dummyApplicant.add("dummy", "", "dummy", "123456789", zipCode);
		return dummyApplicant;
	}

}
