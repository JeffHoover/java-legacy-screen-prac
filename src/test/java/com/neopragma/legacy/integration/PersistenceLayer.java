package com.neopragma.legacy.integration;

import java.io.IOException;
import java.net.URISyntaxException;

import com.neopragma.legacy.screen.JobApplicant;

/*
 * A dummy generic persistence layer to be fleshed out later.
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
